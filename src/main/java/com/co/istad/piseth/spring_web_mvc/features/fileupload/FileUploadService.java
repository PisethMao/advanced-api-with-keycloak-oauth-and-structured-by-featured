package com.co.istad.piseth.spring_web_mvc.features.fileupload;

import com.co.istad.piseth.spring_web_mvc.features.fileupload.dto.FileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    FileResponse upload(MultipartFile file);
}
