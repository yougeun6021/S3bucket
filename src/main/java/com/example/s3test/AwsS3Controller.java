package com.example.s3test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @PostMapping("/api/images")
    public ResponseEntity<?> upload(@RequestPart List<MultipartFile> files) throws IOException {
        return awsS3Service.upload(files);
    }

    @DeleteMapping("/api/images")
    public List<String> deleteImages(@RequestBody List<String> filenames) {
        return awsS3Service.deleteImages(filenames);
    }

}
