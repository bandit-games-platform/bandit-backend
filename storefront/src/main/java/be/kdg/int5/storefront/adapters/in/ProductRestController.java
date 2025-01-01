package be.kdg.int5.storefront.adapters.in;

import be.kdg.int5.common.exceptions.PythonServiceException;
import be.kdg.int5.storefront.adapters.in.dto.ProductDto;
import be.kdg.int5.storefront.domain.CustomerId;
import be.kdg.int5.storefront.domain.ProductProjection;
import be.kdg.int5.storefront.port.in.RecommendationCommand;
import be.kdg.int5.storefront.port.in.RecommendationUseCase;
import be.kdg.int5.storefront.port.in.query.ProductListQuery;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductRestController {
    private final ProductListQuery productListQuery;
    private final RecommendationUseCase recommendationUseCase;

    private final static Logger logger = LoggerFactory.getLogger(ProductRestController.class);

    public ProductRestController(ProductListQuery productListQuery, RecommendationUseCase recommendationUseCase) {
        this.productListQuery = productListQuery;
        this.recommendationUseCase = recommendationUseCase;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<ProductDto>> getRecommendedProducts(@AuthenticationPrincipal Jwt token) {
        final CustomerId customerId = new CustomerId(UUID.fromString(token.getClaimAsString("sub")));

        final RecommendationCommand command = new RecommendationCommand(customerId);
        List<ProductProjection> productList = recommendationUseCase.recommendProducts(command);
        List<ProductDto> productDtoList = productList.stream().map(this::toProductDto).toList();

//        try {
//            List<ProductProjection> productsList = productListQuery.retrieveAllProducts();
//            productDtoList = productsList
//                    .stream()
//                    .map(this::toProductDto)
//                    .toList();
//        } catch (PythonServiceException e) {
//            logger.error("Python service is unavailable at the moment. Please try again later.");
//
//            List<ProductProjection> productsList = productListQuery.retrieveAllProducts();
//            productDtoList = productsList
//                    .stream()
//                    .map(this::toProductDto)
//                    .toList();
//        }
        return ResponseEntity.ok(productDtoList);
    }

    private ProductDto toProductDto(ProductProjection product) {
        return new ProductDto(
                product.getProductId().uuid(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getBackgroundUrl());
    }
}
