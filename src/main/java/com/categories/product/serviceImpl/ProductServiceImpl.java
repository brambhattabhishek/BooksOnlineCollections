package com.categories.product.serviceImpl; // Declares the package where this class resides.

import com.categories.product.dto.productDTO.ProductRequest; // Imports the DTO (Data Transfer Object) used for incoming product data (from the frontend/client).
import com.categories.product.dto.productDTO.ProductResponse; // Imports the DTO used for outgoing product data (to the frontend/client).
import com.categories.product.entities.Category; // Imports the JPA Entity class representing the 'Category' table in the database.
import com.categories.product.entities.Product; // Imports the JPA Entity class representing the 'Product' table in the database.
import com.categories.product.exception.CategoryNotFoundException;
import com.categories.product.mapper.ProductMapper; // Imports the class responsible for converting between DTOs and Entities (Mapper).
import com.categories.product.repositories.CategoryRepository; // Imports the Spring Data JPA repository interface for Category entity operations.
import com.categories.product.repositories.ProductRepository; // Imports the Spring Data JPA repository interface for Product entity operations.
import com.categories.product.services.ProductService; // Imports the ProductService interface that this class implements.
import lombok.AllArgsConstructor; // Imports Lombok annotation to automatically generate a constructor with all fields as arguments.
import org.springframework.beans.factory.annotation.Autowired; // Imports Spring's Autowired annotation (though @AllArgsConstructor often makes it redundant).
import org.springframework.stereotype.Service; // Imports Spring annotation marking this class as a Service component in the Spring application context.

import java.util.List; // Imports the standard Java List collection.

@Service // Marks this class as a Spring Service, a business logic layer component.
@AllArgsConstructor // Generates a constructor that takes all fields (repositories) as arguments for dependency injection.
public class ProductServiceImpl implements ProductService { // Defines the implementation class for the ProductService interface.

    @Autowired // Inject the ProductRepository dependency (used to interact with the Product table).
    private ProductRepository productRepository;
    @Autowired // Inject the CategoryRepository dependency (used to interact with the Category table).
    private CategoryRepository categoryRepository;

    @Override // Indicates that this method is an implementation of a method from the interface.
    public ProductResponse createProduct(ProductRequest productRequest) {
        // The ProductRequest DTO (from frontend) contains: name, description, price, categoryId.
        // Step 1: Check if the categoryId provided in the request is valid/exists in the database.
        // Use categoryRepository to find the Category by its ID.
        // .orElseThrow(() -> new RuntimeException("Category not found")) fetches the category
        // if present, otherwise throws a RuntimeException, stopping execution.
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(()->new CategoryNotFoundException("Category id " + ":"  + productRequest.getCategoryId() + " " + " not found"));
        // The 'category' object now holds the retrieved Category entity (including its ID and name).

        // Step 2: Convert the ProductRequest DTO into a Product Entity.
        // The mapper handles the conversion, linking the new Product to the retrieved Category entity.
        Product product = ProductMapper.toProductEntity(productRequest , category);

        // Step 3: Save the Product Entity to the database using the repository.
        Product savedProduct = productRepository.save(product);

        // Step 4: Convert the saved Product Entity back into a ProductResponse DTO to send back to the client.
        return ProductMapper.toProductResponse(savedProduct);

    }

    @Override // Indicates that this method is an implementation of a method from the interface.
    public ProductResponse updateProduct(Integer id, ProductRequest productRequest) {
        // Step 1: Find the existing Product by its ID from the path variable (id).
        // If not found, throw an exception. This ensures we are updating an existing product.
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        // Note: The next line 'Product savedProduct = productRepository.save(product);' is redundant
        // as 'product' is not modified yet and this save is overwritten later. It can be removed.
        Product savedProduct = productRepository.save(product); // <-- This line appears to be unnecessary/incorrectly placed.

        // Step 2: Find the new Category by its ID from the request DTO.
        // Check if the new categoryId (if changed) is available in the database.
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(()->new RuntimeException("Category not found"));

        // Step 3: Update the fields of the existing 'product' entity with new data from the DTO.
        product.setName(productRequest.getName()); // Update product name.
        product.setDescription(productRequest.getDescription()); // Update product description.
        product.setPrice(productRequest.getPrice()); // Update product price.

        product.setCategory(category); // Update the product's associated Category.

        // Step 4: Save the modified Product Entity back to the database.
        // This performs the actual UPDATE operation.
        productRepository.save(product);


        // Step 5: Convert the updated/saved Product Entity back into a ProductResponse DTO.
        // Note: The variable 'savedProduct' used here holds the state *before* the category/field updates
        // in Step 3/4. It should use the 'product' object after saving.
        return ProductMapper.toProductResponse(savedProduct); // <-- This should ideally use 'product' after the final save.


    }

    @Override // Indicates that this method is an implementation of a method from the interface.
    public void deleteProduct(Integer id) {
        // Use the repository to delete the Product entity corresponding to the given ID.
        productRepository.deleteById(id);
        // Return a confirmation message.

    }

    @Override // Indicates that this method is an implementation of a method from the interface.
    public ProductResponse getProductById(Integer id) {
        // Find the Product entity by its ID.
        // If not found, throw an exception.
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        // Convert the found Product Entity to a ProductResponse DTO and return it.
        return ProductMapper.toProductResponse(product);
    }


    @Override // Indicates that this method is an implementation of a method from the interface.
    public List<ProductResponse> getAllProducts() {
        // Use the repository to fetch all Product entities from the database.
        // .stream() creates a stream of Product entities.
        // .map(ProductMapper::toProductResponse) converts each Product entity in the stream to a ProductResponse DTO.
        // .toList() collects the resulting DTOs into a List and returns it.
        return productRepository.findAll().stream().map(ProductMapper::toProductResponse).toList();
    }

    @Override // Indicates that this method is an implementation of a method from the interface.
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        // This method is currently implemented to return an empty list.
        // The actual logic would involve calling a repository method like productRepository.findByCategoryId(categoryId)
        // and mapping the results to DTOs.
        return List.of();
    }
}