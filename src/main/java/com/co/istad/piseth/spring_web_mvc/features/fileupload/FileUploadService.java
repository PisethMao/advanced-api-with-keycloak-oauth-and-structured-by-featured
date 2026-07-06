package com.co.istad.piseth.spring_web_mvc.features.fileupload;

import com.co.istad.piseth.spring_web_mvc.features.fileupload.dto.FileResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileUploadService {
    Page<FileResponse> getFiles(int page, int size);

    FileResponse findFileByName(String fileName);

    List<FileResponse> uploadMultipartFiles(List<MultipartFile> files) throws IOException;

    FileResponse upload(MultipartFile file);

    void deleteFileByName(String fileName);
}
