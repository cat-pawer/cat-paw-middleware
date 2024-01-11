package com.catpaw.catpawcore.service.file.impl;

import com.catpaw.catpawcore.domain.eumns.TargetType;
import org.springframework.web.multipart.MultipartFile;

public interface FileAppenderState {

    boolean support(String contentType);

    String append(MultipartFile multipartFile, String absoluteFileDestination);

    String createFileKey(String originalFilename, TargetType targetType);
}
