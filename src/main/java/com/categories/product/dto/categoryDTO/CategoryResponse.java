package com.categories.product.dto.categoryDTO;

import com.categories.product.dto.productDTO.ProductResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(

        name = "CategoryResponse",
        description = "it has data of Category which we have to Response To user , it is a part of DTO"

)
public class CategoryResponse {

    private Long id;
    private String name;

    private List<ProductResponse> products = new ArrayList<>();

    public CategoryResponse(Long id, String name, List<ProductResponse> products) {

        this.id = id;
        this.name = name;
        this.products = products;
    }

    public CategoryResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }
}
