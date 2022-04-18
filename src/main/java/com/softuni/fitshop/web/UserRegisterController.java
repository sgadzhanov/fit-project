package com.softuni.fitshop.web;

import com.softuni.fitshop.model.binding.UserRegisterBindingModel;
import com.softuni.fitshop.model.entity.enums.UserExperienceEnum;
import com.softuni.fitshop.model.service.UserRegisterServiceModel;
import com.softuni.fitshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserRegisterController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserRegisterController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("experienceLevels", UserExperienceEnum.values());
        return "auth-register";
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/register")
    public String register(@Valid UserRegisterBindingModel userRegisterBindingModel,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:/users/register";
        }
        if (!userRegisterBindingModel.getConfirmPassword().equals(userRegisterBindingModel.getPassword())) {
            redirectAttributes.addAttribute("diffPasswords", true);
            redirectAttributes.addAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:/users/register";
        }

        UserRegisterServiceModel userRegisterServiceModel =
                this.modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class);

        this.userService.registerAndLoginUser(userRegisterServiceModel);

        return "redirect:/";
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }
}