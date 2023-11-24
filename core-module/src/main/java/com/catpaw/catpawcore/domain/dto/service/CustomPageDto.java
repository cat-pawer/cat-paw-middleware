package com.catpaw.catpawcore.domain.dto.service;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CustomPageDto<T> {

    private List<T> content;

    private int number;

    private int numberOfElements;

    private int size;

    private long totalElements;

    private int totalPages;

    private boolean hasNext;

    private boolean hasPrevious;
}
