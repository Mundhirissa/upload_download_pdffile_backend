package com.example.Upload_and_download_file.Controller;

import com.example.Upload_and_download_file.Model.PdfFile;
import com.example.Upload_and_download_file.Repository.PdfFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class PdfFileUploadController {

    @Autowired
    private PdfFileRepository pdfFileRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdfFile(@RequestParam("file") MultipartFile file) {
        try {
            PdfFile pdfFile = new PdfFile();
            pdfFile.setFilename(file.getOriginalFilename());
            pdfFile.setContentType(file.getContentType());
            pdfFile.setData(file.getBytes());
            pdfFileRepository.save(pdfFile);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }



    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPdfFile(@PathVariable Long id) {
        Optional<PdfFile> optionalPdfFile = pdfFileRepository.findById(id);
        if (optionalPdfFile.isPresent()) {
            PdfFile pdfFile = optionalPdfFile.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(pdfFile.getContentType()));
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(pdfFile.getFilename()).build());
            return new ResponseEntity<>(pdfFile.getData(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/list")
    public ResponseEntity<List<PdfFile>> listAllPdfFiles() {
        List<PdfFile> pdfFiles = pdfFileRepository.findAll();
        if (!pdfFiles.isEmpty()) {
            return ResponseEntity.ok(pdfFiles);
        } else {
            return ResponseEntity.noContent().build();
        }
    }




}
