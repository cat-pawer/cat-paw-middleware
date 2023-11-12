package com.catpaw.catpawmiddleware.service.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class AwsS3Service {

    private final AmazonS3 awsClient;
    private final String bucketName;

    public AwsS3Service(AmazonS3 awsClient, @Value("${cloud.aws.s3.bucket}") String bucketName) {
        this.awsClient = awsClient;
        this.bucketName = bucketName;
    }

    public String uploadFile(MultipartFile multipartFile, String fileName) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new RuntimeException("file is empty");
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        try (InputStream inputStream = multipartFile.getInputStream()) {

            awsClient.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            log.error("Can not upload image, ", e);
            throw new RuntimeException("cannot upload image");
        }

        return awsClient.getUrl(bucketName, fileName).toString();
    }
}
