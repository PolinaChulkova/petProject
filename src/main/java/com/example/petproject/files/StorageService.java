package com.example.petproject.files;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class StorageService {
    private final Path rootLocation;

    public StorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(rootLocation);
    }

    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (file.isEmpty()) {
            throw new RuntimeException("Не удалось сохранить пустой файл" + filename);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить файл" + filename, e);
        }
        return filename;
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }
//    зачем делать load метод если можно просто вызвать сразу resolve

    public Resource loadAsResource(String filename) throws Exception {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;

            } else {
                throw new RuntimeException("Не удалось прочитать файл" + filename);
            }
        } catch (MalformedURLException e) {
           throw new Exception("Не удалось прочитать файл" + filename);
        }
    }
}



