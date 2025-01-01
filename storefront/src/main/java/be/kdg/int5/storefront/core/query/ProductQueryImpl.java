package be.kdg.int5.storefront.core.query;

import be.kdg.int5.storefront.domain.ProductProjection;
import be.kdg.int5.storefront.port.in.query.ProductListQuery;
import be.kdg.int5.storefront.port.out.ProductLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductQueryImpl implements ProductListQuery {
    private final ProductLoadPort productLoadPort;

    public ProductQueryImpl(ProductLoadPort productLoadPort) {
        this.productLoadPort = productLoadPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductProjection> retrieveAllProducts() {
        return productLoadPort.loadAllProducts();
    }
}
