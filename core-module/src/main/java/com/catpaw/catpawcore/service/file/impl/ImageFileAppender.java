package com.catpaw.catpawcore.service.file.impl;

import com.catpaw.catpawcore.domain.eumns.TargetType;
import com.catpaw.catpawcore.service.aws.AwsS3Service;
import com.catpaw.catpawcore.service.file.SupportExtension;
import com.catpaw.catpawcore.service.file.translator.ImageTranslateStrategy;
import com.catpaw.catpawcore.service.file.translator.WebpTranslateStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Set;

@Service
public class ImageFileAppender extends AbstractFileAppender implements FileAppenderState {

    private final ImageTranslateStrategy translateStrategy;

    public ImageFileAppender(AwsS3Service awsS3Service) {
        super(awsS3Service, Set.of(
                SupportExtension.JPEG,
                SupportExtension.JPG,
                SupportExtension.PNG,
                SupportExtension.WEBP
        ));
        this.translateStrategy = new WebpTranslateStrategy();
    }

    @Override
    public String append(MultipartFile multipartFile, String absoluteFileDestination) {
        this.fileValidator(multipartFile);

        File translatedImage = translateStrategy.translate(multipartFile);

        return awsS3Service.uploadFile(translatedImage, absoluteFileDestination);
    }

    @Override
    public String createFileKey(String originalFilename, TargetType targetType) {
        int fileExtensionIndex = originalFilename.lastIndexOf(".");
        String fileName = originalFilename.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        return this.fileDestinationKeyResolver(targetType) +
                fileName +
                "_" +
                now +
                "." +
                SupportExtension.WEBP.name().toLowerCase();
    }
}
