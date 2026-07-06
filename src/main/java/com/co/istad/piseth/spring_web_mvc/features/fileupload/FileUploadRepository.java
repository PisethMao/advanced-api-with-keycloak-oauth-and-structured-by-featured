package com.co.istad.piseth.spring_web_mvc.features.fileupload;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileUploadRepository extends JpaRepository<FileUpload, Integer> {
    Optional<FileUpload> findByFileName(String name);
    boolean existsByFileName(String fileName);
}
