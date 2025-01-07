package be.kdg.int5.storefront.core;

import be.kdg.int5.storefront.domain.ProductId;
import be.kdg.int5.storefront.domain.ProductProjection;
import be.kdg.int5.storefront.port.in.CreateOrUpdateProductProjectionCommand;
import be.kdg.int5.storefront.port.in.CreateOrUpdateProductProjectionUseCase;
import be.kdg.int5.storefront.port.out.ProductCreatePort;
import be.kdg.int5.storefront.port.out.ProductLoadPort;
import be.kdg.int5.storefront.port.out.ProductUpdatePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateOrUpdateProductProjectionUseCaseImpl implements CreateOrUpdateProductProjectionUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateOrUpdateProductProjectionUseCaseImpl.class);
    private final ProductLoadPort productLoadPort;
    private final ProductCreatePort productCreatePort;
    private final ProductUpdatePort productUpdatePort;

    public CreateOrUpdateProductProjectionUseCaseImpl(ProductLoadPort productLoadPort, ProductCreatePort productCreatePort, ProductUpdatePort productUpdatePort) {
        this.productLoadPort = productLoadPort;
        this.productCreatePort = productCreatePort;
        this.productUpdatePort = productUpdatePort;
    }

    @Override
    @Transactional
    public void createOrUpdateProductProjection(CreateOrUpdateProductProjectionCommand command) {
        ProductProjection productProjection = productLoadPort.loadProductById(command.productId());
        if (productProjection == null) {
            productCreatePort.createNewProductProjection(new ProductProjection(
                    new ProductId(command.productId()),
                    command.title(),
                    command.developerId(),
                    command.description(),
                    command.price(),
                    command.backgroundUrl()
            ));
            logger.info("storefront: New projection created for game {}",
                    command.productId());
        } else {
            productProjection.setTitle(command.title());
            productProjection.setDescription(command.description());
            productProjection.setPrice(command.price());
            productProjection.setBackgroundUrl(command.backgroundUrl());
            logger.info("storefront: Existing projection updated for game {}",
                    command.productId());
            productUpdatePort.updatedProductProjection(productProjection);
        }
    }
}
