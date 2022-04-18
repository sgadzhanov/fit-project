package com.softuni.fitshop.service.impl;

import com.softuni.fitshop.model.binding.ProductAddBindingModel;
import com.softuni.fitshop.model.entity.ProductEntity;
import com.softuni.fitshop.model.entity.enums.ProductCategoryEnum;
import com.softuni.fitshop.model.service.ProductUpdateServiceModel;
import com.softuni.fitshop.model.view.ProductDetailsViewModel;
import com.softuni.fitshop.model.view.ProductViewModel;
import com.softuni.fitshop.repository.ProductRepository;
import com.softuni.fitshop.service.ProductService;
import com.softuni.fitshop.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initProducts() {

        if (productRepository.count() != 0) {
            return;
        }
        ProductEntity bench = new ProductEntity();
        bench
                .setBrandName("ArsenalStrength")
                .setCategory(ProductCategoryEnum.WEIGHTS)
                .setName("ALPHA COMPETITION FLAT BENCH")
                .setPrice(BigDecimal.valueOf(900.00))
                .setDescription("The Arsenal Strength Alpha Competition Flat Bench" +
                        " has all the necessary features for a true powerlifting-style bench press training session. " +
                        "Adjustable height bar catches, adjustable face savers, spotter platform, " +
                        "an extra wide pad with grip dot upholstery, " +
                        "and standard band pegs provide you with all the tools you need to get the most out of your bench press.")
                .setImageUrl("https://myarsenalstrength.com/wp-content/uploads/2019/10/Competition-Flat-Bench-1.png");

        ProductEntity stairMaster = new ProductEntity();
        stairMaster
                .setBrandName("Nautilus")
                .setCategory(ProductCategoryEnum.CARDIO)
                .setName("Stairmaster Stepmill SM916")
                .setPrice(BigDecimal.valueOf(2800.00))
                .setDescription("Life may sometimes feel like an uphill climb, but with the StairMaster StepMill 916" +
                        " you’ll enjoy taking it one step at a time. Climbing stairs was once a radical notion in the exercise world," +
                        " but with a sophisticated, smooth machine like the StairMaster StepMill 916 available today," +
                        " stair climbing is almost a must-do part of any serious fitness practitioner’s routine." +
                        " The StairMaster StepMill 916 is the machine that, due to its revolving staircase with 8″ high steps," +
                        " most closely resembles the act of actual stair climbing. Today’s up-to-date model features ergonomically designed handrails," +
                        " which allow for maximum user intensity, accessory options that allow you to store your mp3 player," +
                        " a nationally recognized fitness test to gauge your progress, and custom CPAT and WFI Fire Fighter Tests.")
                .setImageUrl("https://www.fitnessequipmentempire.com/wp-content/uploads/2019/08/web1-18-458x458.jpg");

        ProductEntity bands = new ProductEntity();
        bands
                .setBrandName("SLING SHOT")
                .setCategory(ProductCategoryEnum.BAND)
                .setName("ORIGINAL SLING SHOT")
                .setPrice(BigDecimal.valueOf(63.00))
                .setDescription("Throughout Mark Bell’s career, he has suffered multiple shoulder injuries due to heavy bench press training." +
                        " Being one of the strongest bench pressers in the world, Mark could not just sit out and skip bench pressing due to shoulder pain." +
                        " He wanted to continue to bench heavy. without the pain while reinforcing proper form, so he created the Sling Shot®." +
                        "The Sling Shot® Push Up is specifically designed to assist people in doing a push up properly." +
                        " It will spring your body upwards out of the bottom of the push up and encourage full range of motion.")
                .setImageUrl("https://cdn.shopify.com/s/files/1/2233/5357/products/WEB-ORIGINAL-RED_720x.jpg?v=1629831268");

        this.productRepository.saveAll(Arrays.asList(bench, stairMaster, bands));
    }

    @Override
    public void add(ProductAddBindingModel productAddBindingModel) {
        MultipartFile picture = productAddBindingModel.getPicture();
//        String pictureUrl = "https://img.archiexpo.com/images_ae/photo-mg/67984-12646087.webp"; //todo -> try without picture
        ProductEntity productEntity = this.modelMapper.map(productAddBindingModel, ProductEntity.class);
        productEntity.setCreated(Instant.now());
        String productCategory = productEntity.getCategory().name();

        switch (productCategory) {
            case "BAND" -> productEntity.setImageUrl
                    ("https://thumbs.dreamstime.com/b/black-exercise-bands-isolated-vector-icon-simple-element-illustration-gym-fitness-concept-vector-icons-exercise-bands-143597081.jpg");
            case "CARDIO" -> productEntity.setImageUrl
                    ("https://cdn-icons-png.flaticon.com/512/674/674693.png");
            case "WEIGHTS" -> productEntity.setImageUrl
                    ("https://media.istockphoto.com/vectors/flat-dumbbell-icon-vector-id501279419?k=20&m=501279419&s=170667a&w=0&h=53sylhkg4MzK2Y68Xn4ac_lkUln-aGp6bIVrq_x4Od0=");
        }
        this.productRepository.save(productEntity);
    }

    @Override
    public List<ProductViewModel> getAll() {
        return this.productRepository
                .findAll()
                .stream()
                .map(product -> this.modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> findProductByCategory(ProductCategoryEnum type) {
        return this.productRepository
                .findByCategory(type)
                .stream()
                .map(product -> this.modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetailsViewModel getViewModelById(Long id) {

        return this.productRepository
               .findById(id)
               .map(p -> this.modelMapper.map(p, ProductDetailsViewModel.class))
               .orElseThrow(() -> new ObjectNotFoundException("Product with id " + id + " does not exist!"));
    }


    @Override
    public List<ProductViewModel> getTheNewestProducts() {
        List<ProductEntity> newestProducts = this.productRepository.findTheNewest();

        return this.productRepository
                .findTheNewest()
                .stream()
                .map(product -> this.modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductEntity getById(Long id) {
        return this.productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Product with id " + id + " does not exist!"));
    }

    @Override
    public void update(ProductUpdateServiceModel productUpdateServiceModel) {
        ProductEntity productEntity = this.productRepository
                .findById(productUpdateServiceModel.getId())
                .orElseThrow(() ->
                        new ObjectNotFoundException("Product with id " + productUpdateServiceModel + " does not exist."));

        productEntity
                .setDescription(productUpdateServiceModel.getDescription())
                .setPrice(productUpdateServiceModel.getPrice())
                .setName(productUpdateServiceModel.getName());

        this.productRepository.save(productEntity);
    }

    @Override
    public void deleteProductById(Long id) {
        this.productRepository
                .deleteById(id);
    }

    @Override
    public ProductEntity getByName(String productName) {
        return this.productRepository
                .findByName(productName)
                .orElseThrow(() -> new ObjectNotFoundException("Product " + productName + " does not exist."));
    }

    @Override
    public List<ProductViewModel> getTwoCheapestProducts() {

        return this.productRepository
                .findTheCheapest()
                .stream()
                .limit(2)
                .map(p -> this.modelMapper.map(p, ProductViewModel.class))
                .collect(Collectors.toList());
    }


}