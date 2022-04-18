package com.softuni.fitshop.web;

import com.softuni.fitshop.model.view.OrderProfileViewModel;
import com.softuni.fitshop.model.view.OrderViewModel;
import com.softuni.fitshop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/api")
    public ResponseEntity<List<OrderViewModel>> allOrders() {
        List<OrderViewModel> orders = this.orderService.getAllOrders();

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderProfileViewModel>> getUserOrders(@AuthenticationPrincipal User user) {
        List<OrderProfileViewModel> userOrders = this.orderService.getUserOrdersByUsername(user.getUsername());

        return ResponseEntity.ok(userOrders);
    }

}