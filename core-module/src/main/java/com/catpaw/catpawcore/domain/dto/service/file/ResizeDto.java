package com.catpaw.catpawcore.domain.dto.service.file;

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
