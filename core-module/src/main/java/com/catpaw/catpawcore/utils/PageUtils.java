package com.catpaw.catpawcore.utils;

import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;

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

    public static boolean checkInvalidSort(Pageable pageable, String... properties) {
        if (pageable.getSort().stream().count() != properties.length) return true;

        for (Sort.Order order : pageable.getSort()) {
            if (Arrays.stream(properties).noneMatch(property -> property.equals(order.getProperty()))) {
                return true;
            }
        }
        return false;
    }
}
