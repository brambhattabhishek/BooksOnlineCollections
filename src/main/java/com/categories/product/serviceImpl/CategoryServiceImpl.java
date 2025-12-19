package com.categories.product.serviceImpl; // Declares the package where this class resides.

import com.categories.product.dto.categoryDTO.CategoryRequest; // Imports the DTO for incoming category data (input for create/update).
import com.categories.product.dto.categoryDTO.CategoryResponse; // Imports the DTO for outgoing category data (response after operation).
import com.categories.product.entities.Category; // Imports the JPA Entity class representing the 'Category' table.
import com.categories.product.exception.CategoryAlreadyExistsException;
import com.categories.product.mapper.CategoryMapper; // Imports the class responsible for mapping DTOs to Entities and vice-versa.
import com.categories.product.repositories.CategoryRepository; // Imports the Spring Data JPA repository for Category entity operations.
import com.categories.product.services.CategoryService; // Imports the CategoryService interface that this class implements.
import org.springframework.beans.factory.annotation.Autowired; // Imports Spring's Autowired annotation for dependency injection.
import org.springframework.stereotype.Service; // Imports Spring annotation marking this class as a Service component.

import java.util.List; // Imports the standard Java List collection.
import java.util.Optional; // Imports the Optional class, used for methods that might not return a value (e.g., findById).

@Service // Marks this class as a Spring Service, indicating it holds the business logic.
public class CategoryServiceImpl implements CategoryService { // Defines the implementation class for the CategoryService interface.

    @Autowired // Injects the CategoryRepository dependency, allowing database interaction.
    private CategoryRepository categoryRepository;

    // The CategoryRequest typically contains only category name (and potentially products, depending on design)
    // but not the ID. The CategoryResponse will include the generated ID along with other data.
    @Override // Indicates that this method is an implementation of a method from the interface.
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        // Step 1: Check if a category with the same name already exists in the database.
        // Assumes CategoryRepository has a custom method like findByName(String name).
        Optional<Category> existingCategory = categoryRepository.findByName(categoryRequest.getName());

        if (existingCategory.isPresent()) {


            // If the Optional contains a Category (i.e., the category name exists),
            // Option 1: Convert the existing Category entity to a response DTO and return it.
            throw new CategoryAlreadyExistsException(
                    "Category with name " + categoryRequest.getName() + " already exists"
            );


            // Option 2 (commented out): Throw an exception to prevent duplicate creation.
            // throw new RuntimeException("Category already exists: " + categoryRequest.getName());
        }

        // Step 2: If the category does not exist, convert the CategoryRequest DTO into a Category Entity.
        Category category = CategoryMapper.toCategoryEntity(categoryRequest);

        // Step 3: Save the Category Entity to the database. The repository handles generating the ID.
        Category savedCategory = categoryRepository.save(category);

        // Step 4: Convert the saved Category Entity (now including the ID) to a CategoryResponse DTO and return it.
        return CategoryMapper.toCategoryResponse(savedCategory);
    }


    @Override // Indicates that this method is an implementation of a method from the interface.
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        // This method is currently not fully implemented and returns null.
        // Implementation would involve fetching by ID, updating fields from categoryRequest, and saving.
        return null;
    }

    @Override // Indicates that this method is an implementation of a method from the interface.
    public void deleteCategory(Integer id) {
        // Use the repository to delete the Category entity corresponding to the given ID.
        categoryRepository.deleteById(id);

    }

    @Override // Indicates that this method is an implementation of a method from the interface.
    public CategoryResponse getCategoryById(Long id) {
        // Step 1: Find the Category entity by its ID (using Long ID from method signature).
        // Math.toIntExact(id) converts the Long ID to an Integer ID (assuming the repository expects Integer).
        // .orElseThrow(...) fetches the category if present, otherwise throws a RuntimeException.
        Category category = categoryRepository.findById(Math.toIntExact(id)).orElseThrow(()->new RuntimeException("No category is found"));
        // Step 2: Convert the found Category Entity to a CategoryResponse DTO and return it.
        return CategoryMapper.toCategoryResponse(category);
    }

    @Override // Indicates that this method is an implementation of a method from the interface.
    public List<CategoryResponse> getAllCategories() {
        // The map operation is used to transform (or map) data from one type (Category Entity) to another (CategoryResponse DTO).
        // Step 1: Fetch all Category entities from the database.
        return categoryRepository.findAll().stream()
                // Step 2: Map each Category entity to a CategoryResponse DTO using the mapper.
                .map(CategoryMapper::toCategoryResponse)
                // Step 3: Collect the resulting DTOs into a List and return it.
                .toList();
    }
}