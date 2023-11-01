package com.catpaw.catpawmiddleware.utils;

import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import org.springframework.data.domain.*;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageUtils {

    public static final String CREATED = "created";
    public static final String UPDATED = "updated";

    public static <T> boolean getHasNext(List<T> contents, Pageable pageable) {
        boolean hasNext = false;
        int pageSize = pageable.getPageSize();
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            hasNext = true;
        }

        return hasNext;
    }

    public static boolean checkSortValid(Pageable pageable, String... properties) {
        for (Sort.Order order : pageable.getSort()) {
            if (Arrays.stream(properties).noneMatch(property -> property.equals(order.getProperty()))) {
                return false;
            }
        }
        return true;
    }

    public static <T, R> CustomPageDto<R> createCustomPageDto(Page<T> page, Function<T, R> translate) {
        Assert.notNull(page, "페이지값이 존재하지 않습니다.");
        Assert.notNull(translate, "변환 함수가 존재하지 않습니다.");

        CustomPageDto<R> dto = new CustomPageDto<>();
        dto.setContent(page.getContent().stream().map(translate).toList());
        dto.setNumber(page.getNumber());
        dto.setNumberOfElements(page.getNumberOfElements());
        dto.setSize(page.getSize());
        dto.setTotalElements(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setHasNext(page.hasNext());
        dto.setHasPrevious(page.hasPrevious());

        return dto;
    }

    public static <T, R> CustomPageDto<R> createCustomPageDto(Slice<T> slice, Function<T, R> translate) {
        Assert.notNull(slice, "슬라이스값이 존재하지 않습니다.");
        Assert.notNull(translate, "변환 함수가 존재하지 않습니다.");

        CustomPageDto<R> dto = new CustomPageDto<>();
        dto.setContent(slice.getContent().stream().map(translate).toList());
        dto.setNumber(slice.getNumber());
        dto.setNumberOfElements(slice.getNumberOfElements());
        dto.setSize(slice.getSize());
        dto.setHasNext(slice.hasNext());
        dto.setHasPrevious(slice.hasPrevious());

        return dto;
    }

    public static <T, R> CustomPageDto<R> copyContents(Page<T> origin, List<R> from) {
        Assert.notNull(origin, "페이지값이 존재하지 않습니다.");
        Assert.notNull(from, "복사할 객체가 존재하지 않습니다.");

        CustomPageDto<R> dto = new CustomPageDto<>();
        dto.setContent(from);
        dto.setNumber(origin.getNumber());
        dto.setNumberOfElements(origin.getNumberOfElements());
        dto.setSize(origin.getSize());
        dto.setTotalElements(origin.getTotalElements());
        dto.setTotalPages(origin.getTotalPages());
        dto.setHasNext(origin.hasNext());
        dto.setHasPrevious(origin.hasPrevious());

        return dto;
    }

    public static <T, R> CustomPageDto<R> copyContents(Slice<T> origin, List<R> from) {
        Assert.notNull(origin, "페이지값이 존재하지 않습니다.");
        Assert.notNull(from, "복사할 객체가 존재하지 않습니다.");

        CustomPageDto<R> dto = new CustomPageDto<>();
        dto.setContent(from);
        dto.setNumber(origin.getNumber());
        dto.setNumberOfElements(origin.getNumberOfElements());
        dto.setSize(origin.getSize());
        dto.setHasNext(origin.hasNext());
        dto.setHasPrevious(origin.hasPrevious());

        return dto;
    }



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
