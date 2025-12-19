package com.categories.product;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(
        info = @Info(
                title = "🛒 Product Service API",
                version = "v1.0",
                description = """
                        Welcome to the **Product Service REST API**.  
                        This service provides endpoints for:
                        - 📦 Managing Products  
                        - 🗂️ Managing Categories  
                        - 📝 CRUD Operations  
                        - 🔍 Searching & Filtering  

                        Designed with **clean architecture**, **scalability**, and **high availability** in mind.
                        """,
                contact = @Contact(
                        name = "Abhishek Bhrambhatt",
                        email = "brambhattabhishek@gmail.com",
                        url = "https://www.linkedin.com/in/abhishek-bhrambhatt/"
                ),
                license = @License(
                        name = "Apache 2.0 License",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Local Development Server"
                ),
                @Server(
                        url = "https://api.yourcompany.com",
                        description = "Production Server"
                )
        }
)

@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

}
