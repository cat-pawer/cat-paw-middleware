package com.catpaw.catpawmiddleware.utils;

import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import org.springframework.data.domain.*;

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
        CustomPageDto<R> dto = new CustomPageDto<>();
        dto.setContent(slice.getContent().stream().map(translate).toList());
        dto.setNumber(slice.getNumber());
        dto.setNumberOfElements(slice.getNumberOfElements());
        dto.setSize(slice.getSize());
        dto.setHasNext(slice.hasNext());
        dto.setHasPrevious(slice.hasPrevious());

        return dto;
    }

    public static <T> CustomPageDto<T> createCustomPageDto(Page<T> page) {
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
