package com.softuni.fitshop.service;

import com.softuni.fitshop.model.service.OrderServiceModel;
import com.softuni.fitshop.model.view.OrderProfileViewModel;
import com.softuni.fitshop.model.view.OrderViewModel;

import java.util.List;

public interface OrderService {
    void addOrder(OrderServiceModel orderServiceModel);

    List<OrderViewModel> getAllOrders();

    List<OrderProfileViewModel> getUserOrdersByUsername(String username);
}
