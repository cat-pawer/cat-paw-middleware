package com.catpaw.catpawmiddleware.service.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.catpaw.catpawmiddleware.exception.custom.FileConvertException;
import com.catpaw.catpawmiddleware.utils.FileUtils;
import com.catpaw.catpawmiddleware.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Slf4j
@Service
public class AwsS3Service {

    private final AmazonS3 awsClient;
    private final String bucketName;

    public AwsS3Service(AmazonS3 awsClient, @Value("${cloud.aws.s3.bucket}") String bucketName) {
        this.awsClient = awsClient;
        this.bucketName = bucketName;
    }

    public String uploadFile(MultipartFile multipartFile, String absoluteDestination) {
        try {
            File tempFile = File.createTempFile("temp", FileUtils.getFileExtension(multipartFile));
            multipartFile.transferTo(tempFile);
            String destination = this.uploadFile(tempFile, absoluteDestination);

            this.clearTempFile(tempFile);
            return destination;
        }
        catch (IOException e) {
            throw new FileConvertException();
        }
    }

    public String uploadFile(File file, String absoluteDestination) {
        awsClient.putObject(new PutObjectRequest(bucketName, absoluteDestination, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return awsClient.getUrl(bucketName, absoluteDestination).toString();
    }

    public void removeFile(String absoluteDestination) {
        Assert.hasText(absoluteDestination, LogUtils.notEmptyFormat("absoluteDestination"));

        awsClient.deleteObject(new DeleteObjectRequest(bucketName, absoluteDestination));
    }

    private String getS3Endpoint() {
        return "https://cat-paw-storage.s3.ap-northeast-2.amazonaws.com/";
    }

    private void clearTempFile(File tempFile) {
        boolean delete = tempFile.delete();
        if (!delete) log.error("{}을/를 삭제하지 못했습니다.", tempFile.getName());
    }
}
