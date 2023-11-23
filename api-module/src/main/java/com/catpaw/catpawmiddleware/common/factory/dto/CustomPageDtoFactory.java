package com.catpaw.catpawmiddleware.common.factory.dto;

import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.util.Assert;

public class CustomPageDtoFactory {

    public static <T> CustomPageDto<T> createCustomPageDto(Page<T> page) {
        Assert.notNull(page, "페이지값이 존재하지 않습니다.");

        CustomPageDto<T> dto = new CustomPageDto<>();
        dto.setContent(page.getContent());
        dto.setNumber(page.getNumber());
        dto.setNumberOfElements(page.getNumberOfElements());
        dto.setSize(page.getSize());
        dto.setTotalElements(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setHasNext(page.hasNext());
        dto.setHasPrevious(page.hasPrevious());

        return dto;
    }

    public static <T> CustomPageDto<T> createCustomPageDto(Slice<T> slice) {
        Assert.notNull(slice, "슬라이스값이 존재하지 않습니다.");

        CustomPageDto<T> dto = new CustomPageDto<>();
        dto.setContent(slice.getContent());
        dto.setNumber(slice.getNumber());
        dto.setNumberOfElements(slice.getNumberOfElements());
        dto.setSize(slice.getSize());
        dto.setHasNext(slice.hasNext());
        dto.setHasPrevious(slice.hasPrevious());

        return dto;
    }
}
