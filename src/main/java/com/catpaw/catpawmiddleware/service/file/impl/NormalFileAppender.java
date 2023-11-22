package com.catpaw.catpawmiddleware.service.file.impl;

import com.catpaw.catpawmiddleware.service.aws.AwsS3Service;
import com.catpaw.catpawmiddleware.service.file.SupportExtension;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
public class NormalFileAppender extends AbstractFileAppender implements FileAppenderState {

    public NormalFileAppender(AwsS3Service awsS3Service) {
        super(awsS3Service, Set.of(SupportExtension.TXT, SupportExtension.ZIP, SupportExtension.PDF));
    }

    @Override
    public String append(MultipartFile multipartFile, String absoluteFileDestination) {
        this.fileValidator(multipartFile);

        return awsS3Service.uploadFile(multipartFile, absoluteFileDestination);
    }
}
