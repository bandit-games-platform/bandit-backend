package be.kdg.int5.storefront.adapters.out.python.dto;

import java.util.List;

public class RecommendationRequestDto {
    private List<ProductDto> allProductsList;
    private List<ProductDto> ownedList;

    public RecommendationRequestDto(List<ProductDto> allProductsList, List<ProductDto> ownedList) {
        this.allProductsList = allProductsList;
        this.ownedList = ownedList;
    }

    public List<ProductDto> getAllProductsList() {
        return allProductsList;
    }

    public void setAllProductsList(List<ProductDto> allProductsList) {
        this.allProductsList = allProductsList;
    }

    public List<ProductDto> getOwnedList() {
        return ownedList;
    }

    public void setOwnedList(List<ProductDto> ownedList) {
        this.ownedList = ownedList;
    }
}
