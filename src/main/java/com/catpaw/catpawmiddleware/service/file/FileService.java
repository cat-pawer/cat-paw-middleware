package com.catpaw.catpawmiddleware.service.file;

import com.catpaw.catpawmiddleware.domain.eumns.FileFolder;
import com.catpaw.catpawmiddleware.repository.file.FileRepository;
import com.catpaw.catpawmiddleware.service.aws.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final AwsS3Service awsS3Service;

    public String uploadFile(MultipartFile multipartFile, FileFolder fileFolder) {
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("file이 존재하지 않습니다");
        }

        try {
            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename == null) throw new IllegalArgumentException("파일명이 존재하지 않습니다.");

            return awsS3Service.uploadFile(multipartFile, createFileName(originalFilename, fileFolder));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createFileName(String originalFilename, FileFolder fileFolder) {
        int fileExtensionIndex = originalFilename.lastIndexOf(".");
        String fileExtension = originalFilename.substring(fileExtensionIndex);
        String fileName = originalFilename.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        return fileFolder.getValue() + "/" + fileName + "_" + now + fileExtension;
    }
}
