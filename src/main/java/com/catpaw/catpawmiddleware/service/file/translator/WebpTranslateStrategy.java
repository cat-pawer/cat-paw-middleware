package com.catpaw.catpawmiddleware.service.file.translator;

import com.catpaw.catpawmiddleware.exception.custom.FileConvertException;
import com.catpaw.catpawmiddleware.service.dto.file.ResizeDto;
import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class WebpTranslateStrategy implements ImageTranslateStrategy {

    @Override
    public File translate(MultipartFile multipartFile) {
        ResizeDto resizeDto = this.resize(multipartFile, new ResizeDto(defaultMaxWidth, defaultMaxHeight));

        try {
            return ImmutableImage
                    .loader()
                    .fromBytes(multipartFile.getBytes())
                    .fit(resizeDto.getWidth(), resizeDto.getHeight())
                    .output(WebpWriter.DEFAULT, new File(multipartFile.getName()));
        }
        catch (IOException e) {
            throw new FileConvertException();
        }
    }
}
