package com.categories.product.dto.categoryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(

        name = "CategoryRequest",
        description = "it hodls Requested for adding or updating new category from user it is a part of DTO"

)
public class CategoryRequest {

    private String name;

    public CategoryRequest(String name) {
        this.name = name;
    }
    public CategoryRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
