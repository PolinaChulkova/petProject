package com.example.petproject.controller;

import com.example.petproject.DTO.FileResponseDTO;
import com.example.petproject.files.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Api(description = "Контроллер для работы с файлами")
public class FileController {

    private final StorageService storageService;

    @ApiOperation("Контроллер для загрузки файла")
    @PostMapping("/upload")
    @ResponseBody
    public FileResponseDTO uploadFile(@RequestParam("file") MultipartFile file) {
        String name = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download")
                .path(name)
                .toUriString();

        return new FileResponseDTO(name, uri, file.getContentType(), file.getSize());
    }

    @PostMapping("/upload-multiple-files")
    @ResponseBody
    @ApiOperation("Контроллер для загрузки нескольких файлов")
    public List<FileResponseDTO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    @ApiOperation("Контроллер для скачивания файла")
    public ResponseEntity<Resource> downloadFile (@PathVariable String filename) throws Exception {
        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        resource.getFilename() + "\"")
                .body(resource);
    }

}
