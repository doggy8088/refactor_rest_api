This is a REST API Project developed using Java, Maven, Spring boot and H2 In memory Database.  
The application runs on port 8081

To run this application -
Import this as a Maven Project and run com.example.demo.ProductsApplication as a Java application.

Once the application is running, the entire api documentations is available in following URLs:
http://localhost:8081/v2/api-docs
http://localhost:8081/swagger-ui.html

Logging to H2 Console:
http://localhost:8081/h2-console
use jdbc:h2:mem:testdb as the JDBC URL

The supported APIs are:

1. GET /products - gets all products
Example:
GET - localhost:8081/products/
{
    "items": [
        {
            "id": "f9274b49-6824-4b33-879d-58511c79007e",
            "name": "Apple iPhone 6S",
            "description": "Newest mobile product from Apple.",
            "price": 1299.99,
            "deliveryPrice": 15.99
        },
        {
            "id": "935db1b8-4880-4abd-87fb-9725474d1678",
            "name": "Samsung Galaxy S7",
            "description": "Newest mobile product from Samsung.",
            "price": 1024.99,
            "deliveryPrice": 16.99
        }
    ]
}

2. GET /products?name={name} - finds all products matching the specified name
Example:
GET - localhost:8081/products?name=Samsung Galaxy S7
{
    "items": [
        {
            "id": "935db1b8-4880-4abd-87fb-9725474d1678",
            "name": "Samsung Galaxy S7",
            "description": "Newest mobile product from Samsung.",
            "price": 1024.99,
            "deliveryPrice": 16.99
        }
    ]
}

3. POST /products - creates a new product
Example:
POST - localhost:8081/products/ with body of the request given below:
{
"name": "Samsung Galaxy S7", 
"description":"Newest mobile product from Samsung.", 
"price":1024.99, 
"deliveryPrice":16.99
}

4. DELETE /products/{id} - deletes a product and its options
Example:
DELETE - localhost:8081/products/f9274b49-6824-4b33-879d-58511c79007e

5. GET /products/{id} - gets the project that matches the specified ID - ID is a GUID
Example:
GET - localhost:8081/products/935db1b8-4880-4abd-87fb-9725474d1678
{
    "id": "935db1b8-4880-4abd-87fb-9725474d1678",
    "name": "Samsung Galaxy S7",
    "description": "Newest mobile product from Samsung.",
    "price": 1024.99,
    "deliveryPrice": 16.99
}

6. PUT /products/{id} - updates a product
Example:
PUT - localhost:8081/products/935db1b8-4880-4abd-87fb-9725474d1678 with the Request Body given below:
{
    "name": "Samsung Galaxy S10",
    "description": "Brand New mobile product from Samsung.",
    "price": 1524.99,
    "deliveryPrice": 26.99
}

7. GET /products/{id}/options - finds all options for a specified product
Example: 
GET localhost:8081/products/935db1b8-4880-4abd-87fb-9725474d1678/options
{
    "items": [
        {
            "id": "c97ca910-ab10-4ce6-88f7-70659b58fabf",
            "name": "White",
            "description": "White Samsung Galaxy S7"
        },
        {
            "id": "fbaf8843-a3b1-4796-ba2c-71054dd81550",
            "name": "Black",
            "description": "Black Samsung Galaxy S7"
        }
    ]
}
8. POST /products/{id}/options - adds a new product option to the specified product
Example:
POST - localhost:8081/products/935db1b8-4880-4abd-87fb-9725474d1678/options with the request body given below:
{
"name": "White", 
"description":"White Samsung Galaxy S7"
}

9. DELETE /products/{id}/options/{optionId} - deletes the specified product option
Example:
DELETE - localhost:8081/products/935db1b8-4880-4abd-87fb-9725474d1678/options/c97ca910-ab10-4ce6-88f7-70659b58fabf

10. GET /products/{id}/options/{optionId} - finds the specified product option for the specified product
Example:
GET - localhost:8081/products/935db1b8-4880-4abd-87fb-9725474d1678/options/c97ca910-ab10-4ce6-88f7-70659b58fabf
{
    "id": "c97ca910-ab10-4ce6-88f7-70659b58fabf",
    "name": "White",
    "description": "White Samsung Galaxy S7"
}

11. PUT /products/{id}/options/{optionId} - updates the specified product option
Example:
PUT - localhost:8081/products/935db1b8-4880-4abd-87fb-9725474d1678/options/fbaf8843-a3b1-4796-ba2c-71054dd81550
{
"name": "Red", 
"description":"Red Samsung Galaxy S7"
}

