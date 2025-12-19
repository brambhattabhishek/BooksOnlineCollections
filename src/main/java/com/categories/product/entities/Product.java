package com.categories.product.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")     // 👈 Real DB table name
@Data

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
       // SQL Column: id
    private Long id;          // Example: 1


    private String name;      // Example: "iPhone 15"


    private String description;  // Example: "Latest Apple flagship smartphone"


    private Double price;        // Example: 79999.00

    // category_id column will store the ID of the Category table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;



    /*
        ============================
        Dummy Example (SQL Reality)
        ============================

        products table:
        ------------------------------------------------------------------------------------
        | id |        name        |               description               |  price  | category_id |
        ------------------------------------------------------------------------------------
        | 1  | iPhone 15          | Latest Apple flagship smartphone        | 79999   |     1       |
        | 2  | Samsung TV 55-inch | 4K LED Smart TV                        | 45000   |     1       |
        | 3  | T-Shirt            | Cotton half-sleeves men T-shirt        | 599     |     2       |
        | 4  | Milk 1 Litre       | Fresh cow milk                         | 55      |     3       |
        ------------------------------------------------------------------------------------

        Category Table (For Reference)
        -------------------------------------------------
        | id |      name      |
        -------------------------------------------------
        | 1  | Electronics    |
        | 2  | Clothing       |
        | 3  | Groceries      |
        -------------------------------------------------

        Java Object Example:
        ------------------------------------
        Category electronics = new Category(1, "Electronics", null);

        Product iphone = new Product(
              1,
              "iPhone 15",
              "Latest Apple flagship smartphone",
              79999.0,
              electronics
        );
    */


    public Product(Long id, String name, String description, Double price, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public Product() {}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
