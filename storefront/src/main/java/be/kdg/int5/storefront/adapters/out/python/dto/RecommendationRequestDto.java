package be.kdg.int5.storefront.adapters.out.python.dto;

import java.util.List;

public class RecommendationRequestDto {
    private List<ProductDto> allProductsList;

    public RecommendationRequestDto(List<ProductDto> allProductsList) {
        this.allProductsList = allProductsList;
    }

    public List<ProductDto> getAllProductsList() {
        return allProductsList;
    }

    public void setAllProductsList(List<ProductDto> allProductsList) {
        this.allProductsList = allProductsList;
    }
}
