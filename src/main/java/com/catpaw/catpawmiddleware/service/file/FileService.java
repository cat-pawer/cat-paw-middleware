package com.catpaw.catpawmiddleware.service.file;

import com.catpaw.catpawmiddleware.common.factory.dto.FileDtoFactory;
import com.catpaw.catpawmiddleware.domain.entity.FileMaster;
import com.catpaw.catpawmiddleware.exception.custom.DataNotFoundException;
import com.catpaw.catpawmiddleware.service.dto.file.FileSummaryDto;
import com.catpaw.catpawmiddleware.service.dto.file.FileTarget;
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

import java.util.List;
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

    public List<FileSummaryDto> getFileSummaryList(FileTarget fileTarget) {
        Assert.notNull(fileTarget, LogUtils.notNullFormat("fileTarget"));

        List<FileMaster> fileMasterList = fileRepository.findFileMasterByFileTarget(fileTarget);

        return fileMasterList.stream()
                .map(FileDtoFactory::toFileSummary)
                .toList();
    }

    public String upload(MultipartFile multipartFile, FileTarget fileTarget) {
        Assert.notNull(multipartFile, LogUtils.notNullFormat("multipartFile"));
        Assert.notNull(fileTarget, LogUtils.notNullFormat("fileTarget"));

        FileAppenderState fileAppender = this.getFileAppender(multipartFile.getContentType());
        final String fileKey =
                fileAppender.createFileKey(multipartFile.getOriginalFilename(), fileTarget.targetType());

        final String destination = fileAppender.append(multipartFile, fileKey);

        FileMaster fileMaster = new FileMaster(
                fileTarget.targetId(),
                fileTarget.targetType(),
                destination,
                multipartFile.getOriginalFilename(),
                fileKey
        );

        fileRepository.save(fileMaster);

        return destination;
    }

    public void remove(long fileId) {
        FileMaster fileMaster = fileRepository.findById(fileId)
                .orElseThrow(() -> { throw new DataNotFoundException(); });

        awsS3Service.removeFile(fileMaster.getFileKey());
        fileRepository.delete(fileMaster);
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
