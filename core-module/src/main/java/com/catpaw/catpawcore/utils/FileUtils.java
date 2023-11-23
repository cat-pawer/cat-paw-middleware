package com.catpaw.catpawcore.utils;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    public static String getFileExtension(MultipartFile multipartFile) {
        String originalFileName = multipartFile.getOriginalFilename();
        if (originalFileName != null) {
            int lastDotIndex = originalFileName.lastIndexOf(".");
            if (lastDotIndex != -1) {
                return originalFileName.substring(lastDotIndex + 1);
            }
        }
        return null;
    }
}
