package com.catpaw.catpawmiddleware.service.file;

import com.catpaw.catpawmiddleware.domain.entity.FileMaster;
import com.catpaw.catpawmiddleware.domain.model.file.FileTarget;
import com.catpaw.catpawmiddleware.exception.custom.NoSupportContentTypeException;
import com.catpaw.catpawmiddleware.repository.file.FileRepository;
import com.catpaw.catpawmiddleware.service.aws.AwsS3Service;
import com.catpaw.catpawmiddleware.service.file.impl.FileAppenderState;
import com.catpaw.catpawmiddleware.service.file.impl.ImageFileAppender;
import com.catpaw.catpawmiddleware.service.file.impl.NormalFileAppender;
import com.catpaw.catpawmiddleware.utils.LogUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final AwsS3Service awsS3Service;
    private final Set<FileAppenderState> fileAppenderStateSet;

    public FileService(FileRepository fileRepository, AwsS3Service awsS3Service) {
        this.fileRepository = fileRepository;
        this.awsS3Service = awsS3Service;
        this.fileAppenderStateSet = Set.of(
                new NormalFileAppender(awsS3Service),
                new ImageFileAppender(awsS3Service)
        );
    }

    public String upload(MultipartFile multipartFile, FileTarget fileTarget) {
        FileAppenderState fileAppender = this.getFileAppender(multipartFile.getContentType());
        String absoluteFileDestination =
                fileAppender.createAbsoluteFileDestination(multipartFile.getOriginalFilename(), fileTarget.getTargetType());

        String destination = fileAppender.append(multipartFile, absoluteFileDestination);

        FileMaster fileMaster = new FileMaster(
                        fileTarget.getTargetId(), fileTarget.getTargetType(), destination, multipartFile.getOriginalFilename());

        fileRepository.save(fileMaster);

        return destination;
    }

    private FileAppenderState getFileAppender(String contentType) {
        Assert.hasText(contentType, LogUtils.notEmptyFormat("contentType"));
        return this.fileAppenderStateSet.stream()
                .filter(appender -> appender.support(contentType))
                .findAny()
                .orElseThrow(() -> {
                    throw new NoSupportContentTypeException("지원하지 하는 파일 타입입니다.");
        });
    }
}
