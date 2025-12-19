package com.categories.product.controller;

import com.categories.product.dto.productDTO.ProductRequest;
import com.categories.product.dto.productDTO.ProductResponse;
import com.categories.product.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Product REST API",
        description = "CRUD operations for Product"
)
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    // Service layer dependency
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ================= CREATE PRODUCT =================
    @Operation(
            summary = "Create Product",
            description = "REST API to create a new product"
    )
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody ProductRequest productRequest
    ) {
        ProductResponse response = productService.createProduct(productRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ================= UPDATE PRODUCT =================
    @Operation(
            summary = "Update Product",
            description = "REST API to update an existing product"
    )
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Integer id,
            @RequestBody ProductRequest productRequest
    ) {
        ProductResponse response = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(response);
    }

    // ================= DELETE PRODUCT =================
    @Operation(
            summary = "Delete Product",
            description = "REST API to delete a product by ID"
    )
    @ApiResponse(responseCode = "200", description = "Product deleted successfully")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    // ================= GET PRODUCT BY ID =================
    @Operation(
            summary = "Get Product by ID",
            description = "REST API to fetch a product using its ID"
    )
    @ApiResponse(responseCode = "200", description = "Product fetched successfully")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id) {
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    // ================= GET ALL PRODUCTS =================
    @Operation(
            summary = "Get All Products",
            description = "REST API to fetch all products"
    )
    @ApiResponse(responseCode = "200", description = "Products fetched successfully")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // ================= GET PRODUCTS BY CATEGORY =================
    @Operation(
            summary = "Get Products by Category",
            description = "REST API to fetch products by category ID"
    )
    @ApiResponse(responseCode = "200", description = "Products fetched successfully")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @PathVariable Long categoryId
    ) {
        List<ProductResponse> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }
}
