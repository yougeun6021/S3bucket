package com.example.s3test;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AwsS3Service {

    private final AmazonS3 amazonS3;



    @Value("${S3.bucket.name}")
    private final String bucket;


    public ResponseEntity<?> upload(List<MultipartFile> files) throws IOException {

        for(MultipartFile file:files){
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            PutObjectRequest por = new PutObjectRequest(bucket, filename, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(por);
        }

        return new ResponseEntity<>("성공적으로 파일이 업로드 되었습니다",HttpStatus.valueOf(200));


    }


    public List<String> deleteImages(List<String> filenames) {
        for (String filename : filenames) {
            amazonS3.deleteObject(bucket,filename);
        }

        return filenames;
    }


}