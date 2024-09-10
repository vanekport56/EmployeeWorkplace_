package com.example.employeeworkplace;

import com.example.employeeworkplace.Services.DocumentGeneratorService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EmployeeWorkplaceApplication {

    private final DocumentGeneratorService documentGeneratorService;

    public EmployeeWorkplaceApplication(DocumentGeneratorService documentGeneratorService) {
        this.documentGeneratorService = documentGeneratorService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EmployeeWorkplaceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        documentGeneratorService.generateAndSaveDocuments();
    }
}
