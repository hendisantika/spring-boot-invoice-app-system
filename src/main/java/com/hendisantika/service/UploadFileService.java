package com.hendisantika.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app-system
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/09/21
 * Time: 10.05
 */
@Service
public class UploadFileService {
    private final static String UPLOADS_FOLDER = "uploads";
    private final Logger log = LoggerFactory.getLogger(getClass());

    public Resource load(String filename) throws MalformedURLException {
        Path pathPhoto = getPath(filename);
        log.info("pathPhoto: " + pathPhoto);
        Resource resource = null;

        resource = new UrlResource(pathPhoto.toUri());

        if (!resource.exists() && !resource.isReadable()) {
            throw new RuntimeException("Error: can not load image" + pathPhoto);
        }

        return resource;
    }

    public String copy(MultipartFile file) throws IOException {
        String uniqueFilename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path rootPath = getPath(uniqueFilename);
        log.info("rootPath: " + rootPath);

        Files.copy(file.getInputStream(), rootPath);

        // byte[] bytes = photo.getBytes();
        // Path rootComplete = Paths.get(rootPath + "//" + photo.getOriginalFilename());
        // Files.write(rootComplete, bytes);

        return uniqueFilename;
    }
}
