package com.softuni.fitshop.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Map;

//@Configuration
//@Primary
public class CloudinaryConfig {
//    @Value("${cloudinary.cloud-name}")
    private String cloudName;
//    @Value("${cloudinary.api-key}")
    private String apiKey;
//    @Value("${cloudinary.api-secret}")
    private String apiSecret;


//    @Bean
    public Cloudinary createCloudinaryConfig() {
        return new Cloudinary(Map.of(
            "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }
}