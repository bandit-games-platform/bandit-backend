package be.kdg.int5.storefront.adapters.out.db.product;

import be.kdg.int5.storefront.domain.ProductId;
import be.kdg.int5.storefront.domain.ProductProjection;
import be.kdg.int5.storefront.port.out.ProductCreatePort;
import be.kdg.int5.storefront.port.out.ProductLoadPort;
import be.kdg.int5.storefront.port.out.ProductUpdatePort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ProductJpaAdapter implements ProductCreatePort, ProductLoadPort, ProductUpdatePort {
    private final ProductProjectionJpaRepository productProjectionJpaRepository;

    public ProductJpaAdapter(ProductProjectionJpaRepository productProjectionJpaRepository) {
        this.productProjectionJpaRepository = productProjectionJpaRepository;
    }

    @Override
    public void createNewProductProjection(ProductProjection productProjection) {
        ProductProjectionJpaEntity productProjectionJpaEntity = new ProductProjectionJpaEntity(
                productProjection.getProductId().uuid(),
                productProjection.getTitle(),
                productProjection.getDeveloperId(),
                productProjection.getDescription(),
                productProjection.getPrice(),
                productProjection.getBackgroundUrl()
        );
        productProjectionJpaRepository.save(productProjectionJpaEntity);
    }

    @Override
    public ProductProjection loadProductById(UUID productId) {
        ProductProjectionJpaEntity productProjectionJpaEntity = productProjectionJpaRepository.findById(productId).orElse(null);
        if (productProjectionJpaEntity == null) return null;
        return new ProductProjection(
                new ProductId(productProjectionJpaEntity.getId()),
                productProjectionJpaEntity.getTitle(),
                productProjectionJpaEntity.getDeveloperId(),
                productProjectionJpaEntity.getPrice()
        );
    }

    @Override
    public List<ProductProjection> loadAllProducts() {
        List<ProductProjectionJpaEntity> productListJpa = productProjectionJpaRepository.findAll();
        return productListJpa
                .stream()
                .map(this::toProductProjection)
                .toList();
    }

    @Override
    public void updatedProductProjection(ProductProjection productProjection) {
        ProductProjectionJpaEntity productProjectionJpaEntity = productProjectionJpaRepository.findById(
                productProjection.getProductId().uuid()
        ).orElse(null);
        if (productProjectionJpaEntity == null) {
            createNewProductProjection(productProjection);
            return;
        }
        productProjectionJpaEntity.setTitle(productProjection.getTitle());
        productProjectionJpaEntity.setDescription(productProjection.getDescription());
        productProjectionJpaEntity.setPrice(productProjection.getPrice());
        productProjectionJpaEntity.setBackgroundUrl(productProjection.getBackgroundUrl());
        productProjectionJpaRepository.save(productProjectionJpaEntity);
    }

    private ProductProjection toProductProjection(ProductProjectionJpaEntity productJpa) {
        return new ProductProjection(
                new ProductId(productJpa.getId()),
                productJpa.getTitle(),
                productJpa.getDeveloperId(),
                productJpa.getDescription(),
                productJpa.getPrice(),
                productJpa.getBackgroundUrl()
        );
    }
}
