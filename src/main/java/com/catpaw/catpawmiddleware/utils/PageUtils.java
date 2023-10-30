package com.catpaw.catpawmiddleware.utils;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageUtils {

    public static <T> boolean getHasNext(List<T> contents, Pageable pageable) {
        boolean hasNext = false;
        int pageSize = pageable.getPageSize();
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            hasNext = true;
        }

        return hasNext;
    }
}
