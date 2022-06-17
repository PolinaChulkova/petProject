package com.example.petproject;

import com.example.petproject.files.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class PetProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetProjectApplication.class, args);
    }

//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setMaxFileSize(DataSize.parse("128KB"));
//        factory.setMaxRequestSize(DataSize.parse("128KB"));
//        return factory.createMultipartConfig();
//    }
}
