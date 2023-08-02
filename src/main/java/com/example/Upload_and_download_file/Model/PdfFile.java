package com.example.Upload_and_download_file.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PdfFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private String contentType;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    // Getters and setters
}
