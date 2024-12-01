package be.kdg.int5.storefront.adapters.out.db.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductProjectionJpaRepository extends JpaRepository<ProductProjectionJpaEntity, UUID> {
}
