package com.categories.product.controller;

import com.categories.product.dto.categoryDTO.CategoryRequest;
import com.categories.product.dto.categoryDTO.CategoryResponse;
import com.categories.product.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Category REST API",
        description = "CRUD operations for Category"
)
@RestController
@RequestMapping("/api/categories")

public class CategoryController {

    // Service layer dependency (constructor injected)

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ================= CREATE CATEGORY =================
    @Operation(
            summary = "Create Category",
            description = "REST API to create a new category"
    )
    @ApiResponse(responseCode = "201", description = "Category created successfully")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestBody CategoryRequest categoryRequest
    ) {
        CategoryResponse response = categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ================= DELETE CATEGORY =================
    @Operation(
            summary = "Delete Category",
            description = "REST API to delete a category by ID"
    )
    @ApiResponse(responseCode = "200", description = "Category deleted successfully")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    // ================= GET CATEGORY BY ID =================
    @Operation(
            summary = "Get Category by ID",
            description = "REST API to fetch a category using its ID"
    )
    @ApiResponse(responseCode = "200", description = "Category fetched successfully")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }

    // ================= GET ALL CATEGORIES =================
    @Operation(
            summary = "Get All Categories",
            description = "REST API to fetch all categories"
    )
    @ApiResponse(responseCode = "200", description = "Categories fetched successfully")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
