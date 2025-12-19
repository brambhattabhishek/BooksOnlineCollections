package com.categories.product.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories")    // 👈 Actual DB table name
@Data

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
           // SQL column: id
    private Long id;            // Example: 1


    private String name;        // Example: "Electronics"

    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL,   // delete category -> product auto delete
                   // removing product from list deletes it
            fetch = FetchType.EAGER
    )
    private List<Product> products;

    /*
        ======================
        Dummy Database Example
        ======================

        categories table:
        ----------------------------------------------------
        | id |     name        |
        ----------------------------------------------------
        | 1  | Electronics     |
        | 2  | Clothing        |
        | 3  | Groceries       |
        ----------------------------------------------------

        products table (for reference):
        ------------------------------------------------------------------------------------
        | id |       name        |     description                       | price | category_id |
        ------------------------------------------------------------------------------------
        | 1  | iPhone 15         | Apple smartphone                      | 79999 |     1       |
        | 2  | Samsung TV        | 55-inch Smart TV                      | 45000 |     1       |
        | 3  | Men T-Shirt       | Cotton T-shirt                        | 599   |     2       |
        | 4  | Milk 1L           | Fresh cow milk                        | 55    |     3       |
        ------------------------------------------------------------------------------------

        Java object example:
        Category electronics = new Category(
            1,
            "Electronics",
            [iPhone 15, Samsung TV]
        );
     */

    public Category(Long id, String name, List<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public Category() {}

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
