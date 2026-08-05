package com.canteen.management.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) {

        try {

            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.emptyMap()
            );

            return uploadResult.get("secure_url").toString();

        } catch (Exception e) {
            System.err.println("Cloudinary Image Upload Failed: " + e.getMessage() + ". Falling back to local upload.");
            try {
                String uploadDir = "uploads";
                java.io.File directory = new java.io.File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                
                String originalFilename = file.getOriginalFilename();
                if (originalFilename == null || originalFilename.isEmpty()) {
                    originalFilename = "food.jpg";
                }
                String filename = java.util.UUID.randomUUID().toString() + "_" + originalFilename;
                java.nio.file.Path filePath = java.nio.file.Paths.get(uploadDir, filename);
                java.nio.file.Files.write(filePath, file.getBytes());
                
                return "/uploads/" + filename;
            } catch (Exception ex) {
                System.err.println("Local Image Upload Fallback Failed: " + ex.getMessage());
                return "";
            }
        }

    }
}