# EasyShop E-Commerce API
## Overview
EasyShop is a full-stack e-commerce application built using Java, Spring Boot, MySQL, and a front-end web interface. This project allows users to browse products, manage shopping carts, place orders, and securely access protected resources through authentication and authorization.
The goal of this project was to apply REST API development principles, database integration, Spring Security, and full-stack application development while following industry-standard development practices.
---
## Features
### User Features
- Browse products by category
- Search and filter products
- Register and authenticate users
- Add items to shopping cart
- Update cart quantities
- Remove items from cart
- Checkout and place orders
- View order history
### Admin Features
- Create products
- Update products
- Delete products
- Manage categories
- Access protected endpoints through role-based authorization
---
## Technologies Used
- Java
- Spring Boot
- Spring Security
- MySQL
- JDBC
- Maven
- Insomnia
- HTML
- CSS
- JavaScript
- Git & GitHub
---
## Screenshots
### Home Page
![Home Page](images/homepage.png)
### Product Catalog
![Products](images/products.png)
### Shopping Cart
![Cart](images/cart.png)
### Insomnia Endpoint Testing
!Images/Screenshot 2026-06-23 085839.png
---
## Interesting Code
One of the most interesting parts of this project was implementing role-based security using Spring Security. This allowed certain endpoints to be accessible only by administrators while protecting customer data and shopping cart functionality.
```java
@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
   product.delete(id);
   return ResponseEntity.noContent().build()