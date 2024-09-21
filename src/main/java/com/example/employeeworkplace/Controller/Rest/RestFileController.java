package com.example.employeeworkplace.Controller.Rest;

import com.example.employeeworkplace.Models.Primary.FileEntity;
import com.example.employeeworkplace.Models.Primary.OrderedDocuments;
import com.example.employeeworkplace.Repositories.Primary.FileRepository;
import com.example.employeeworkplace.Repositories.Primary.OrderedDocumentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

/**
 * Контроллер для обработки операций с файлами, таких как загрузка и скачивание файлов.
 */
@Slf4j
@RestController
@RequestMapping("/api/files")
public class RestFileController {


    private final Path fileStorageLocation = Paths.get("/path/to/storage").toAbsolutePath().normalize();
    private final FileRepository fileRepository;
    private final OrderedDocumentsRepository orderedDocumentsRepository;

    public RestFileController(FileRepository fileRepository, OrderedDocumentsRepository orderedDocumentsRepository) {
        this.fileRepository = fileRepository;
        this.orderedDocumentsRepository = orderedDocumentsRepository;
    }

    /**
     * Обрабатывает загрузку файла на сервер и обновляет информацию о файле в базе данных.
     *
     * @param file файл, который загружается
     * @param documentId идентификатор документа, который связывается с файлом
     * @return сообщение о результате операции загрузки файла
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentId") Long documentId) {
        log.info("Попытка загрузить файл: {} для документа с ID: {}", file.getOriginalFilename(), documentId);

        try {

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                throw new IllegalArgumentException("Значение не может быть null");
            }
            Path targetLocation = fileStorageLocation.resolve(originalFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            log.debug("Файл {} сохранен на сервере в: {}", file.getOriginalFilename(), targetLocation);


            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setFilePath(targetLocation.toString());
            fileEntity.setFileSize(file.getSize());
            fileEntity.setUploadTime(LocalDateTime.now());
            fileRepository.save(fileEntity);

            log.debug("Запись о файле сохранена в базе данных: {}", fileEntity);


            OrderedDocuments document = orderedDocumentsRepository.findById(documentId)
                    .orElseThrow(() -> {
                        log.error("Документ с ID {} не найден", documentId);
                        return new RuntimeException("Document not found");
                    });

            document.setFile(fileEntity);
            orderedDocumentsRepository.save(document);

            log.info("Файл {} успешно загружен и документ {} обновлен", file.getOriginalFilename(), documentId);

            return ResponseEntity.ok("File uploaded and document updated successfully.");
        } catch (IOException e) {
            log.error("Ошибка при загрузке файла: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed.");
        }
    }

    /**
     * Обрабатывает запрос на скачивание файла по имени файла.
     *
     * @param fileName имя файла, который необходимо скачать
     * @return файл в виде ресурса или сообщение об ошибке
     */
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        log.info("Запрос на скачивание файла: {}", fileName);

        try {

            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                log.debug("Файл {} найден, подготовка к скачиванию", fileName);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                log.warn("Файл {} не найден", fileName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IOException e) {
            log.error("Ошибка при скачивании файла: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
