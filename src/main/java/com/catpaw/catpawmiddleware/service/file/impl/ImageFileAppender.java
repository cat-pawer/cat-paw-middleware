package com.catpaw.catpawmiddleware.service.file.impl;

import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import com.catpaw.catpawmiddleware.service.aws.AwsS3Service;
import com.catpaw.catpawmiddleware.service.file.SupportExtension;
import com.catpaw.catpawmiddleware.service.file.translator.ImageTranslateStrategy;
import com.catpaw.catpawmiddleware.service.file.translator.WebpTranslateStrategy;
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

        File convertedImage = translateStrategy.translate(multipartFile);

        return awsS3Service.uploadFile(convertedImage, absoluteFileDestination);
    }

    @Override
    public String createFileKey(String originalFilename, TargetType targetType) {
        int fileExtensionIndex = originalFilename.lastIndexOf(".");
        String fileName = originalFilename.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        return this.fileDestinationResolver(targetType) + fileName + "_" + now + ".webp";
    }
}
