package com.softuni.fitshop.init;

import com.softuni.fitshop.service.ProductService;
import com.softuni.fitshop.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Terminal implements CommandLineRunner {
    private final ProductService productService;
    private final UserService userService;

    public Terminal(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.productService.initProducts();
        this.userService.initUsers();
    }
}