package com.categories.product.services; // Declares the package where this interface resides.

import com.categories.product.dto.categoryDTO.CategoryRequest; // Imports the DTO used for data input (creation/update).
import com.categories.product.dto.categoryDTO.CategoryResponse; // Imports the DTO used for data output (response).

import java.util.List; // Imports the standard Java List collection, used for methods returning multiple categories.

public interface CategoryService { // Defines the CategoryService interface, outlining the contract for category business operations.

    //Create
    // Method signature for creating a new category. Takes a CategoryRequest DTO and returns the saved CategoryResponse DTO.
    CategoryResponse createCategory(CategoryRequest categoryRequest);

    //Update
    // Method signature for updating an existing category. Takes the category ID and the CategoryRequest DTO for new data, returning the updated CategoryResponse DTO.
    CategoryResponse updateCategory(Long id , CategoryRequest categoryRequest);

    //delete
    // Method signature for deleting a category by its ID. It performs the action and does not return a body (void).
    void deleteCategory(Integer id);

    //GET BY ID
    // Method signature for fetching a single category by its ID, returning a CategoryResponse DTO.
    CategoryResponse getCategoryById(Long id);

    //GET ALL
    // Method signature for fetching all categories, returning a List of CategoryResponse DTOs.
    List<CategoryResponse> getAllCategories();
}