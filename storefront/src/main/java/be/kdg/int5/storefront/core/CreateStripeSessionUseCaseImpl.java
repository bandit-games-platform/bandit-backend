package be.kdg.int5.storefront.core;

import be.kdg.int5.storefront.domain.ProductProjection;
import be.kdg.int5.storefront.port.in.CreateStripeSessionCommand;
import be.kdg.int5.storefront.port.in.CreateStripeSessionUseCase;
import be.kdg.int5.storefront.port.out.ProductLoadPort;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.PriceSearchResult;
import com.stripe.model.Product;
import com.stripe.model.ProductSearchResult;
import com.stripe.model.checkout.Session;
import com.stripe.param.*;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateStripeSessionUseCaseImpl implements CreateStripeSessionUseCase {
    @Value("${contexts.frontend.baseUrl}")
    private String FRONTEND_URL;
    private final ProductLoadPort productLoadPort;

    public CreateStripeSessionUseCaseImpl(ProductLoadPort productLoadPort) {
        this.productLoadPort = productLoadPort;
    }

    @Override
    public Session createStripeSession(CreateStripeSessionCommand command) throws StripeException {
        ProductProjection basicGameDetails = productLoadPort.loadProductById(command.productId());
        if (basicGameDetails == null) return null;

        String productName = basicGameDetails.getTitle();
        BigDecimal productPrice = basicGameDetails.getPrice().multiply(BigDecimal.valueOf(100)); // Convert euro price into cent price

        Price price = findProductPrice(command.productId().toString(), productName, productPrice);

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setReturnUrl(FRONTEND_URL + "/store/" + command.gameId() + "/purchased?session_id={CHECKOUT_SESSION_ID}")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPrice(price.getId())
                                        .build())
                        .build();

        return Session.create(params);
    }

    /**
     * This method is responsible for finding or creating an existing Stripe product and finding a price for that product
     * If that price no longer matches the current price then a new price is created and the old one invalidated
     * If no price or no price matching the current price has been found for that product then a new price is created
     * @param productId The id of the product to search for, this is the gameId in our case
     * @param productName The name of the product to search for, this is the game title in our case
     * @param productPrice The price of the product in cents, the game price in cents
     * @return Returns a Stripe price object
     * @throws StripeException If something goes wrong within stripe this exception gets thrown
     */
    private Price findProductPrice(String productId, String productName, BigDecimal productPrice) throws StripeException {
        Product product = findProductByName(productId, productName);

        PriceSearchParams searchPrice = PriceSearchParams.builder()
                .setQuery(String.format("product:'%s' AND active:'true'", product.getId()))
                .build();
        PriceSearchResult foundPrice = Price.search(searchPrice);

        Price price;
        if (foundPrice.getData().isEmpty()) {
            price = createNewPrice(product, productPrice);
        } else {
            Price matchingPrice = null;
            for (Price loadedPrice : foundPrice.getData()) {
                if (loadedPrice.getUnitAmount() == productPrice.longValue()) {
                    matchingPrice = loadedPrice;
                } else {
                    PriceUpdateParams updatePrice = PriceUpdateParams.builder()
                            .setActive(false)
                            .build();
                    loadedPrice.update(updatePrice);
                }
            }
            if (matchingPrice == null) {
                price = createNewPrice(product, productPrice);
            } else {
                price = matchingPrice;
            }
        }
        return price;
    }

    /**
     * Creates a new price for the stripe product
     * @param product A stripe product object, used to link the price to
     * @param productPrice The price to set the product at
     * @return Returns a stripe product object
     * @throws StripeException If something goes wrong within stripe this exception gets thrown
     */
    private Price createNewPrice(Product product, BigDecimal productPrice) throws StripeException {
        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setProduct(product.getId())
                .setUnitAmount(productPrice.longValue()) // This is in cents
                .setCurrency("eur")
                .build();
        return Price.create(priceParams);
    }

    /**
     * This method is responsible for finding a product on name,
     * and if no such product is found then a new one is created
     * @param productId The id of the product to use in creation if no existing product is found
     * @param productName The name of the product to search for, which in our case is game title
     * @return Returns a stripe product object
     * @throws StripeException If something goes wrong within stripe this exception gets thrown
     */
    private Product findProductByName(String productId, String productName) throws StripeException {
        ProductSearchParams searchProduct = ProductSearchParams.builder()
                .setQuery(String.format("name:'%s' AND active:'true'", productName))
                .build();
        ProductSearchResult foundProducts = Product.search(searchProduct);

        Product product;
        if (foundProducts.getData().isEmpty()) {
            ProductCreateParams productParams = ProductCreateParams.builder()
                    .setName(productName)
                    .setId(productId)
                    .build();
            product = Product.create(productParams);
        } else {
            product = foundProducts.getData().getFirst();
        }
        return product;
    }
}
