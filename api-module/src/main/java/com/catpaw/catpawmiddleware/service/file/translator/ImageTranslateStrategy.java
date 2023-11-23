package com.catpaw.catpawmiddleware.service.file.translator;

import com.catpaw.catpawcore.exception.custom.FileConvertException;
import com.catpaw.catpawmiddleware.service.dto.file.ResizeDto;
import com.catpaw.catpawcore.utils.LogUtils;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageTranslateStrategy {

    Integer defaultMaxWidth = 300;
    Integer defaultMaxHeight = 300;

    File translate(MultipartFile multipartFile);

    default ResizeDto resize(MultipartFile multipartFile, ResizeDto resizeDto) {
        Assert.notNull(multipartFile, LogUtils.notNullFormat("multipartFile"));
        Assert.notNull(resizeDto, LogUtils.notNullFormat("resizeDto"));
        try {
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            int originalWidth = image.getWidth();
            int originalHeight = image.getHeight();
            int maxWidth = resizeDto.getWidth() == null ? defaultMaxWidth : resizeDto.getWidth();
            int maxHeight = resizeDto.getHeight() == null ? defaultMaxHeight : resizeDto.getHeight();
            int resizedWidth = originalWidth;
            int resizedHeight = originalHeight;

            if (originalWidth > maxWidth || originalHeight > maxHeight) {
                double wRatio = (double) maxWidth / originalWidth;
                double hRatio = (double) maxHeight / originalHeight;
                double minRatio = Math.min(wRatio, hRatio);
                resizedWidth = (int) (originalWidth * minRatio);
                resizedHeight = (int) (originalHeight * minRatio);
            }
            return new ResizeDto(resizedWidth, resizedHeight);
        }
        catch (IOException e) {
            throw new FileConvertException("resize fail");
        }
    }
}
