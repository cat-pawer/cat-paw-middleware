package com.catpaw.catpawmiddleware.common.factory.dto;

import com.catpaw.catpawcore.domain.entity.Category;
import com.catpaw.catpawcore.domain.entity.CategoryMapper;
import com.catpaw.catpawmiddleware.service.dto.category.CategorySummaryDto;

public class CategoryDtoFactory {

    public static CategorySummaryDto toCategorySummary(CategoryMapper categoryMapper) {
        CategorySummaryDto dto = new CategorySummaryDto();
        dto.setCategoryMapperId(categoryMapper.getId());
        dto.setCategoryId(categoryMapper.getCategory().getId());
        dto.setTargetId(categoryMapper.getTargetId());
        dto.setTargetType(categoryMapper.getType());
        dto.setName(categoryMapper.getCategory().getName());
        dto.setValue(categoryMapper.getCategory().getValue());
        dto.setCategoryType(categoryMapper.getCategory().getType());
        dto.setCreated(categoryMapper.getCreated());

        return dto;
    }

    public static CategorySummaryDto toCategorySummary(Category category) {
        CategorySummaryDto dto = new CategorySummaryDto();
        dto.setCategoryId(category.getId());
        dto.setName(category.getName());
        dto.setValue(category.getValue());
        dto.setCategoryType(category.getType());
        dto.setCreated(category.getCreated());

        return dto;
    }
}
