package com.catpaw.catpawmiddleware.service.file.impl;

import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import org.springframework.web.multipart.MultipartFile;

public interface FileAppenderState {

    boolean support(String contentType);

    String append(MultipartFile multipartFile, String absoluteFileDestination);

    String createAbsoluteFileDestination(String originalFilename, TargetType targetType);
}
