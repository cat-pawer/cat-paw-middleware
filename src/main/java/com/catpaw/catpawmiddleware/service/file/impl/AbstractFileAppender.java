package com.catpaw.catpawmiddleware.service.file.impl;

import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import com.catpaw.catpawmiddleware.service.aws.AwsS3Service;
import com.catpaw.catpawmiddleware.service.file.FileDestination;
import com.catpaw.catpawmiddleware.service.file.SupportExtension;
import com.catpaw.catpawmiddleware.utils.LogUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public abstract class AbstractFileAppender implements FileAppenderState {

    protected final Set<SupportExtension> supportExtensionSet;
    protected final AwsS3Service awsS3Service;

    public AbstractFileAppender(AwsS3Service awsS3Service, Set<SupportExtension> supportExtensionSet) {
        this.awsS3Service = awsS3Service;
        this.supportExtensionSet = supportExtensionSet;
    }

    @Override
    public boolean support(String contentType) {
        return supportExtensionSet.stream()
                .map(extension -> extension.name().toLowerCase())
                .anyMatch(contentType::contains);
    }

    public String createFileKey(String originalFilename, TargetType targetType) {
        Assert.hasText(originalFilename, LogUtils.notEmptyFormat("originalFilename"));
        Assert.notNull(targetType, LogUtils.notNullFormat("targetType"));

        int fileExtensionIndex = originalFilename.lastIndexOf(".");
        String fileExtension = originalFilename.substring(fileExtensionIndex);
        String fileName = originalFilename.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        return this.fileDestinationResolver(targetType) + fileName + "_" + now + fileExtension;
    }

    protected void fileValidator(MultipartFile multipartFile) {
        Assert.notNull(multipartFile, LogUtils.notNullFormat("multipartFile"));

        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException(LogUtils.notEmptyFormat("multipartFile"));
        }
        if (!StringUtils.hasText(multipartFile.getOriginalFilename())) {
            throw new IllegalArgumentException(LogUtils.notEmptyFormat("originFileName"));
        }
    }

    protected String fileDestinationResolver(TargetType targetType) {
        Assert.notNull(targetType, LogUtils.notNullFormat("targetType"));
        StringBuilder sb = new StringBuilder();

        switch (targetType) {
            case GROUP_BOARD -> {
                sb.append(FileDestination.GROUP_BOARD.getValue());
            }
            case RECRUIT -> {
                sb.append(FileDestination.RECRUIT.getValue());
            }
            default -> {
                throw new IllegalArgumentException("파일 경로가 옳바르지 않습니다.");
            }
        }
        return sb.append("/").toString();
    }
}
