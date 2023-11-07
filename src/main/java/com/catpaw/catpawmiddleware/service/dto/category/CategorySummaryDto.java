package com.catpaw.catpawmiddleware.service.dto.category;

import com.catpaw.catpawmiddleware.domain.eumns.CategoryType;
import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CategorySummaryDto {

    private Long categoryMapperId;

    private Long categoryId;

    private Long targetId;

    private TargetType targetType;

    private CategoryType categoryType;

    private String name;

    private String value;

    private LocalDateTime created;
}
