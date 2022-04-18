package com.softuni.fitshop.web;

import com.softuni.fitshop.model.binding.UserPictureBindingModel;
import com.softuni.fitshop.model.service.UserPictureServiceModel;
import com.softuni.fitshop.model.view.UserViewModel;
import com.softuni.fitshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class UserProfileController {
    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/profile")
    public String profile(Model model, Principal principal) {
        UserViewModel viewModel = this.userService.getViewModelByUsername(principal.getName());
        model.addAttribute("userViewModel", viewModel);

        return "profile";
    }

    @PatchMapping("/users/profile/{id}")
    public String profile(UserPictureBindingModel userPictureBindingModel, Principal principal, @PathVariable Long id) {
        UserPictureServiceModel userPictureServiceModel = new UserPictureServiceModel();
        userPictureServiceModel.setPicture(userPictureBindingModel.getPicture())
                .setUsername(principal.getName());
        this.userService.updateWithPicture(userPictureServiceModel);

        return "redirect:/users/profile";
    }

    @ModelAttribute
    public UserPictureBindingModel userPictureBindingModel() {
        return new UserPictureBindingModel();
    }
}