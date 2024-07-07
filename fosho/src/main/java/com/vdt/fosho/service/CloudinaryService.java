package com.vdt.fosho.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {

    Cloudinary cloudinary;

    public CloudinaryService() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", System.getenv("CLOUD_NAME"));
        config.put("api_key", System.getenv("API_KEY"));
        config.put("api_secret", System.getenv("API_SECRET"));
        cloudinary = new Cloudinary(config);
    }

    // Upload image to Cloudinary
    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Map result = cloudinary.uploader().upload(file, new HashMap());

        // Delete the temporary file if it exists
        if (!Files.deleteIfExists(file.toPath())) {
            throw new IOException("Failed to delete temporary file: " + file.getAbsolutePath());
        }
        return result;
    }

    // Delete image from Cloudinary
    public Map delete(String id) throws IOException {
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }

    // Convert MultipartFile to File
    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }
}
