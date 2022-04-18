package com.softuni.fitshop.service.impl;

import com.cloudinary.Cloudinary;
import com.softuni.fitshop.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

//@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private static final String URL = "url";
    private static final String TEMP_FILE = "temp_file";
    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public String uploadPicture(MultipartFile multipartFile) throws IOException {
        File tempFile = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);

        try {
            Map<String, String> output = this.cloudinary
                    .uploader()
                    .upload(tempFile, Map.of("sample", "fs"));

            return output
                    .getOrDefault(
                            URL,
                            "https://res.cloudinary.com/algruev/image/upload/v1636620629/Fitshop/default_h96tkx.jpg"
                    );
        } finally {
            tempFile.delete();
        }
    }
}