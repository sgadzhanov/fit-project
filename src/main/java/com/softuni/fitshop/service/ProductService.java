package com.softuni.fitshop.service;

import com.softuni.fitshop.model.binding.ProductAddBindingModel;
import com.softuni.fitshop.model.binding.ProductUpdateBindingModel;
import com.softuni.fitshop.model.entity.ProductEntity;
import com.softuni.fitshop.model.entity.enums.ProductCategoryEnum;
import com.softuni.fitshop.model.service.ProductUpdateServiceModel;
import com.softuni.fitshop.model.view.ProductDetailsViewModel;
import com.softuni.fitshop.model.view.ProductViewModel;

import java.util.List;

public interface ProductService {
    void initProducts();

    void add(ProductAddBindingModel productAddBindingModel);

    List<ProductViewModel> getAll();

    List<ProductViewModel> findProductByCategory(ProductCategoryEnum type);

    ProductDetailsViewModel getViewModelById(Long id);

    List<ProductViewModel> getTheNewestProducts();

    ProductEntity getById(Long id);

    void update(ProductUpdateServiceModel productUpdateServiceModel);

    void deleteProductById(Long id);

    ProductEntity getByName(String productName);

    List<ProductViewModel> getTwoCheapestProducts();
}
