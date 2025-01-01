package be.kdg.int5.storefront.adapters.out.python;

import be.kdg.int5.common.exceptions.PythonServiceException;
import be.kdg.int5.storefront.adapters.in.ProductRestController;
import be.kdg.int5.storefront.adapters.out.python.dto.ProductDto;
import be.kdg.int5.storefront.adapters.out.python.dto.RecommendationRequestDto;
import be.kdg.int5.storefront.domain.ProductProjection;
import be.kdg.int5.storefront.port.out.ProductRecommendationPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class PythonSystemAdapter implements ProductRecommendationPort {
    @Value("${python.url:http://localhost:8000}")
    private String pythonUrl;

    private static final String RECOMMEND_PRODUCTS = "/recommend";

    private final RestTemplate restTemplate;

    private final static Logger logger = LoggerFactory.getLogger(ProductRestController.class);

    public PythonSystemAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
//    public String getRecommendationsForCustomer(List<ProductProjection> allProductsList, List<ProductProjection> ownedList) {
    public String getRecommendationsForCustomer(List<ProductProjection> allProductsList) {
        final List<ProductDto> productDtos = productDtoList(allProductsList);
        final RecommendationRequestDto recommendationRequestDto = new RecommendationRequestDto(productDtos);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RecommendationRequestDto> entity = new HttpEntity<>(recommendationRequestDto, headers);

        logger.info(entity.toString());

        try {
            return restTemplate.postForObject(pythonUrl + RECOMMEND_PRODUCTS, entity, String.class);

        } catch (Exception e) {
            throw new PythonServiceException("An error occurred while calling the Python Recommender Service.", e);
        }
    }

    private List<ProductDto> productDtoList(List<ProductProjection> allProductsList) {
        return allProductsList.stream()
                .map(p -> new ProductDto(
                        p.getProductId().uuid(),
                        p.getTitle(),
                        p.getDeveloperId(),
                        p.getDescription()))
                .toList();
    }
}
