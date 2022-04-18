package com.softuni.fitshop.web;

import com.softuni.fitshop.model.binding.OrderBindingModel;
import com.softuni.fitshop.model.service.OrderServiceModel;
import com.softuni.fitshop.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final ModelMapper modelMapper;
    private final OrderService orderService;

    public OrderController(ModelMapper modelMapper, OrderService orderService) {
        this.modelMapper = modelMapper;
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public String allOrders() {
        return "orders-all";
    }

    @GetMapping("/new/{name}")
    public String newOrder(@PathVariable String name, Model model) {
        model.addAttribute("productName", name);

        return "order-new";
    }

    @PostMapping("/new/{name}")
    public String newOrder(@Valid OrderBindingModel orderBindingModel,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes,
                           @PathVariable("name") String productName,
                           @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("orderBindingModel", orderBindingModel);
            redirectAttributes.addAttribute("org.springframework.validation.BindingResult.orderBindingModel", bindingResult);

            return "redirect:/orders/new/{name}";
        }
        OrderServiceModel orderServiceModel = this.modelMapper.map(orderBindingModel, OrderServiceModel.class);
        orderServiceModel.setProductName(productName)
                         .setClientUsername(user.getUsername());

        this.orderService.addOrder(orderServiceModel);

        return "redirect:/users/profile";
    }

    @ModelAttribute
    public OrderBindingModel orderBindingModel() {
        return new OrderBindingModel();
    }
}