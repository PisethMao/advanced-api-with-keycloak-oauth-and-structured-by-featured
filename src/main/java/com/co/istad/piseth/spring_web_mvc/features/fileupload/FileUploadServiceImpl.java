package com.co.istad.piseth.spring_web_mvc.features.fileupload;

import com.co.istad.piseth.spring_web_mvc.features.fileupload.dto.FileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final FileUploadRepository fileUploadRepository;
    private final FileUploadMapper fileUploadMapper;

    @Value("${file-upload.server-path}")
    private String serverPath;

    @Override
    public Page<FileResponse> getFiles(int page, int size) {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sortById);
        Page<FileUpload> fileUploads = fileUploadRepository.findAll(pageable);
        return fileUploads.map(fileUploadMapper::mapper);
    }

    @Override
    public FileResponse findFileByName(String fileName) {
        return fileUploadRepository.findByFileName(fileName)
                .map(fileUploadMapper::mapper)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "File with name " + fileName + " not found."
                ));
    }

    @Override
    public List<FileResponse> uploadMultipartFiles(List<MultipartFile> files) {
        return files.stream()
                .map(this::saveFile)
                .collect(Collectors.toList());
    }

    @Override
    public FileResponse upload(MultipartFile file) {
        return saveFile(file);
    }

    @Override
    public void deleteFileByName(String fileName) {
        FileUpload fileUpload = fileUploadRepository.findByFileName(fileName)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "File with name " + fileName + " not found."
                ));
        Path filePath = Paths.get(String.format(
                "%s%s.%s",
                serverPath,
                fileUpload.getFileName(),
                fileUpload.getFileExt()
        ));
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not delete file " + fileName
            );
        }
        fileUploadRepository.delete(fileUpload);
    }

    private FileResponse saveFile(MultipartFile file) {
        String fileName = UUID.randomUUID().toString();
        String fileExt = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Path filePath = Paths.get(String.format("%s%s.%s", serverPath, fileName, fileExt));
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFileName(fileName);
        fileUpload.setFileExt(fileExt);
        fileUpload.setMediaType(file.getContentType());
        fileUpload.setSize(file.getSize());
        fileUploadRepository.save(fileUpload);
        return fileUploadMapper.mapper(fileUpload);
    }
}
