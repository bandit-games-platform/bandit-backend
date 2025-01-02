package be.kdg.int5.storefront.adapters.out.python;

import be.kdg.int5.common.exceptions.PythonServiceException;
import be.kdg.int5.storefront.adapters.in.ProductRestController;
import be.kdg.int5.storefront.adapters.out.python.dto.ProductDto;
import be.kdg.int5.storefront.adapters.out.python.dto.RecommendationRequestDto;
import be.kdg.int5.storefront.domain.ProductProjection;
import be.kdg.int5.storefront.port.out.ProductRecommendationPort;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Repository
public class PythonSystemAdapter implements ProductRecommendationPort {
    @Value("${python.url:http://localhost:8000}")
    private String pythonUrl;

    private static final String RECOMMEND_PRODUCTS = "/recommend";

    private final RestTemplate restTemplate;

    private final static Logger logger = LoggerFactory.getLogger(PythonSystemAdapter.class);

    public PythonSystemAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ProductProjection> getRecommendationsForCustomer(List<ProductProjection> allProductsList, List<ProductProjection> ownedProductsList) {
        final List<ProductDto> productDtos = productDtoList(allProductsList);
        final List<ProductDto> ownedDtos = productDtoList(ownedProductsList);
        final RecommendationRequestDto recommendationRequestDto = new RecommendationRequestDto(productDtos, ownedDtos);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RecommendationRequestDto> entity = new HttpEntity<>(recommendationRequestDto, headers);

        try {
            String response = restTemplate.postForObject(pythonUrl + RECOMMEND_PRODUCTS, entity, String.class);
            Set<String> recommendationIds = parseResponse(response);

            // complicated stream in order to preserve the order of the recommendationsIds
            return recommendationIds.stream()
                    .map(id -> allProductsList.stream()
                            .filter(p -> p.getProductId().uuid().toString().equals(id))
                            .findFirst().orElse(null))
                    .filter(Objects::nonNull)
                    .toList();

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

    private Set<String> parseResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(response);

            if (jsonNode.has("response")) {
                JsonNode responseArray = jsonNode.get("response");

                Set<String> recommendedIds = new LinkedHashSet<>();
                for (JsonNode node : responseArray) {
                    recommendedIds.add(node.asText());
                }

                return recommendedIds;
            } else {
                throw new IllegalArgumentException("Response JSON does not contain 'response' field");
            }
        } catch (Exception e) {
            logger.error("Error while parsing the Python service response", e);
            throw new PythonServiceException("Failed to parse Python service response.", e);
        }
    }
}
