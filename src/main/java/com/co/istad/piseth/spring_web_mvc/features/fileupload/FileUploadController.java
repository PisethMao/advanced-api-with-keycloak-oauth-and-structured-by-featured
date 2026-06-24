package com.co.istad.piseth.spring_web_mvc.features.fileupload;

import com.co.istad.piseth.spring_web_mvc.features.fileupload.dto.FileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping
    public FileResponse upload(@RequestPart MultipartFile file) {
        return fileUploadService.upload(file);
    }
}
