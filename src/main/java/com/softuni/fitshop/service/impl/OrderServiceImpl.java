package com.softuni.fitshop.service.impl;

import com.softuni.fitshop.model.entity.OrderEntity;
import com.softuni.fitshop.model.entity.ProductEntity;
import com.softuni.fitshop.model.entity.UserEntity;
import com.softuni.fitshop.model.service.OrderServiceModel;
import com.softuni.fitshop.model.view.OrderProfileViewModel;
import com.softuni.fitshop.model.view.OrderViewModel;
import com.softuni.fitshop.repository.OrderRepository;
import com.softuni.fitshop.repository.ProductRepository;
import com.softuni.fitshop.service.OrderService;
import com.softuni.fitshop.service.ProductService;
import com.softuni.fitshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public OrderServiceImpl(ModelMapper modelMapper, OrderRepository orderRepository, UserService userService, ProductService productService, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public void addOrder(OrderServiceModel orderServiceModel) {
        OrderEntity order = this.modelMapper.map(orderServiceModel, OrderEntity.class);
        UserEntity client = this.userService.getByUsername(orderServiceModel.getClientUsername());
        ProductEntity product = this.productService.getByName(orderServiceModel.getProductName());

        order.setCreated(Instant.now())
                .setClient(client)
                .setProduct(product);

        this.orderRepository.save(order);
        this.productRepository.save(product.setOrdered(true));
    }

    @Override
    public List<OrderViewModel> getAllOrders() {
        return this.orderRepository
                .findAll()
                .stream()
                .map(order -> {
                    OrderViewModel orderViewModel = new OrderViewModel();
                    orderViewModel.setClientFullName(order.getClientFullName())
                            .setProductName(order.getProductName())
                            .setCreated(order.getCreated());

                    return orderViewModel;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<OrderProfileViewModel> getUserOrdersByUsername(String username) {

        return this.orderRepository
                .findAllByClientUsername(username)
                .stream()
                .map(o -> {
                    OrderProfileViewModel orderProfileViewModel = new OrderProfileViewModel();
                    orderProfileViewModel.setProductName(o.getProductName())
                            .setAddress(o.getAddress())
                            .setProductBrandName(o.getProduct().getBrandName())
                            .setCreated(o.getCreated());

                    return orderProfileViewModel;
                })
                .collect(Collectors.toList());
    }
}