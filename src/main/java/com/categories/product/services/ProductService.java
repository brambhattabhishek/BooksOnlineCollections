package com.categories.product.services; // Declares the package where this interface resides.

import com.categories.product.dto.productDTO.ProductRequest; // Imports the DTO used for data input (creation/update).
import com.categories.product.dto.productDTO.ProductResponse; // Imports the DTO used for data output (response).

import java.util.List; // Imports the standard Java List collection, used for methods returning multiple products.

public interface ProductService { // Defines the ProductService interface, outlining the contract for product business operations.

    // CREATE
    // Method signature for creating a new product. Takes a ProductRequest DTO and returns the saved ProductResponse DTO.
    ProductResponse createProduct(ProductRequest request);

    // UPDATE
    // Method signature for updating an existing product. Takes the product ID and the ProductRequest DTO for new data, returning the updated ProductResponse DTO.
    ProductResponse updateProduct(Integer id, ProductRequest request);

    // DELETE
    // Method signature for deleting a product by its ID. It returns a String confirmation message upon completion.
    void deleteProduct(Integer id);

    // GET BY ID
    // Method signature for fetching a single product by its ID, returning a ProductResponse DTO.
    ProductResponse getProductById(Integer id);

    // GET ALL
    // Method signature for fetching all products, returning a List of ProductResponse DTOs.
    List<ProductResponse> getAllProducts();

    // GET ALL PRODUCTS BY CATEGORY
    // Method signature for fetching products filtered by a specific category ID, returning a List of ProductResponse DTOs.
    List<ProductResponse> getProductsByCategory(Long categoryId);
}