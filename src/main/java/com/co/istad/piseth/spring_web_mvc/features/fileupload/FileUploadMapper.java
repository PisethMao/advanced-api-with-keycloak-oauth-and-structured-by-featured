package com.co.istad.piseth.spring_web_mvc.features.fileupload;

import com.co.istad.piseth.spring_web_mvc.features.fileupload.dto.FileResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileUploadMapper {
    @Value("${file-upload.base-uri}")
    private String baseUi;

    public FileResponse mapper(FileUpload fileUpload) {
        return FileResponse.builder()
                .name(fileUpload.getFileName())
                .fileExt(fileUpload.getFileExt())
                .mediaType(fileUpload.getMediaType())
                .size(fileUpload.getSize())
                .uri(baseUi + "/" + fileUpload.getFileName() + "." + fileUpload.getFileExt())
                .build();
    }
}
