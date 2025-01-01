package be.kdg.int5.storefront.adapters.in;

import be.kdg.int5.storefront.adapters.in.dto.ProductDto;
import be.kdg.int5.storefront.domain.ProductProjection;
import be.kdg.int5.storefront.port.in.query.ProductListQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductRestController {
    private final ProductListQuery productListQuery;

    public ProductRestController(ProductListQuery productListQuery) {
        this.productListQuery = productListQuery;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getRecommendedProducts() {
        List<ProductProjection> productsList = productListQuery.retrieveAllProducts();
        List<ProductDto> productDtoList = productsList
                .stream()
                .map(this::toProductDto)
                .toList();
        return ResponseEntity.ok(productDtoList);
    }

    private ProductDto toProductDto(ProductProjection product) {
        return new ProductDto(
                product.getProductId().uuid(),
                product.getTitle(),
                product.getPrice());
    }
}
