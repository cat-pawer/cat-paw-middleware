package com.catpaw.catpawmiddleware.service.dto.file;

import lombok.Getter;

@Getter
public class ResizeDto {

    private final Integer width;
    private final Integer height;

    public ResizeDto(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }
}
