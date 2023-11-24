package com.catpaw.catpawcore.domain.dto.service.category;

import com.catpaw.catpawcore.domain.eumns.CategoryType;
import com.catpaw.catpawcore.domain.eumns.TargetType;
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
