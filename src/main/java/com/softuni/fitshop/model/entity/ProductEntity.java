package com.softuni.fitshop.model.entity;

import com.softuni.fitshop.model.entity.enums.ProductCategoryEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "brand_name", nullable = false)
    private String brandName;
    @Enumerated(EnumType.STRING)
    private ProductCategoryEnum category;
    @Lob
    @Column(nullable = false, length = 500)
    private String description;
    @Column(nullable = false)
    private String imageUrl;
    @Column
    private Instant created;
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private OrderEntity order;
    private Boolean isOrdered;

    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getBrandName() {
        return brandName;
    }

    public ProductEntity setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public ProductCategoryEnum getCategory() {
        return category;
    }

    public ProductEntity setCategory(ProductCategoryEnum category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Instant getCreated() {
        return created;
    }

    public ProductEntity setCreated(Instant created) {
        this.created = created;
        return this;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public ProductEntity setOrder(OrderEntity order) {
        this.order = order;
        return this;
    }

    public Boolean getOrdered() {
        return isOrdered;
    }

    public ProductEntity setOrdered(Boolean ordered) {
        isOrdered = ordered;
        return this;
    }
}