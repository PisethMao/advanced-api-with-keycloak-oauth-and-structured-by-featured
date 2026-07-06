package com.co.istad.piseth.spring_web_mvc.features.fileupload;

import com.co.istad.piseth.spring_web_mvc.features.fileupload.dto.FileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FileResponse upload(@RequestPart MultipartFile file) {
        return fileUploadService.upload(file);
    }

    @PostMapping("/multiple")
    @ResponseStatus(HttpStatus.CREATED)
    public List<FileResponse> uploadMultiple(@RequestPart List<MultipartFile> file) throws IOException {
        return fileUploadService.uploadMultipartFiles(file);
    }

    @GetMapping
    public Page<FileResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "25") Integer size) {
        return fileUploadService.getFiles(page, size);
    }

    @GetMapping("/{name}")
    public FileResponse findFileByName(@PathVariable String name) {
        return fileUploadService.findFileByName(name);
    }

    @DeleteMapping("/{fileName:.+}")
    public ResponseEntity<?> deleteFileByName(@PathVariable String fileName) {
        fileUploadService.deleteFileByName(fileName);
        return ResponseEntity.ok(Map.of(
                "message", "File deleted successfully",
                "fileName", fileName
        ));
    }
}
