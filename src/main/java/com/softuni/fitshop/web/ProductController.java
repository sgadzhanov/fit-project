package com.softuni.fitshop.web;

import com.softuni.fitshop.model.binding.ProductAddBindingModel;
import com.softuni.fitshop.model.binding.ProductUpdateBindingModel;
import com.softuni.fitshop.model.entity.ProductEntity;
import com.softuni.fitshop.model.entity.enums.ProductCategoryEnum;
import com.softuni.fitshop.model.service.ProductUpdateServiceModel;
import com.softuni.fitshop.model.view.ProductDetailsViewModel;
import com.softuni.fitshop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public ProductAddBindingModel productAddBindingModel() {
        return new ProductAddBindingModel();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/add")
    public String addProduct() {
        return "product-add";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public String addProduct(@Valid ProductAddBindingModel productAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addAttribute("org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);

            return "redirect:/add";
        }
        this.productService.add(productAddBindingModel);

        return "redirect:/products/all";
    }

    @GetMapping("/all")
    public String allProducts(Model model) {
        model.addAttribute("products", this.productService.getAll());
        return "products";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/details/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        ProductDetailsViewModel productModel = this.productService.getViewModelById(id);
        model.addAttribute("productModel", productModel);

        return "product-details";
    }

    @GetMapping("/weights")
    public String weights(Model model) {
        model.addAttribute("products", this.productService.findProductByCategory(ProductCategoryEnum.WEIGHTS));
        return "products";
    }

    @GetMapping("/bands")
    public String bands(Model model) {
        model.addAttribute("products", this.productService.findProductByCategory(ProductCategoryEnum.BAND));
        return "products";
    }

    @GetMapping("/cardio")
    public String cardio(Model model) {
        model.addAttribute("products", this.productService.findProductByCategory(ProductCategoryEnum.CARDIO));
        return "products";
    }

    @GetMapping("/new")
    public String newest(Model model) {
        model.addAttribute("products", this.productService.getTheNewestProducts());
        return "products";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, Model model) {
        ProductEntity productEntity = this.productService.getById(id); //todo
        ProductUpdateBindingModel productUpdateBindingModel = this.modelMapper
                .map(productEntity, ProductUpdateBindingModel.class);

        model.addAttribute("productUpdateBindingModel", productUpdateBindingModel);

        return "product-update";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public String updateProduct(@Valid ProductUpdateBindingModel productUpdateBindingModel,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("productUpdateBindingModel", productUpdateBindingModel);
            redirectAttributes.addAttribute("org.springframework.validation.BindingResult.productUpdateBindingModel", bindingResult);

            return "redirect:/products/update/" + id + "/error";

        }
        this.productService
                .update(this.modelMapper.map(productUpdateBindingModel, ProductUpdateServiceModel.class));

        return "redirect:/";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        this.productService.deleteProductById(id);

        return "redirect:/products/all";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/update/{id}/error")
    public String updateError(@PathVariable Long id) {
        return "product-update";
    }
}