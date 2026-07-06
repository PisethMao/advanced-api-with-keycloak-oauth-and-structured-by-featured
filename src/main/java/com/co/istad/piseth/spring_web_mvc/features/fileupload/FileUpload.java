package com.co.istad.piseth.spring_web_mvc.features.fileupload;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "file")
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String fileName;
    @Column(nullable = false, length = 6)
    private String fileExt;
    @Column(nullable = false, length = 15)
    private String mediaType;
    @Column(nullable = false)
    private Long size;
}
