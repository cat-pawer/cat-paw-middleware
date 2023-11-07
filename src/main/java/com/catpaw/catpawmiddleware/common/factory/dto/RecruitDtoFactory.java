package com.catpaw.catpawmiddleware.common.factory.dto;

import com.catpaw.catpawmiddleware.domain.entity.Category;
import com.catpaw.catpawmiddleware.domain.entity.CategoryMapper;
import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.domain.eumns.CategoryType;
import com.catpaw.catpawmiddleware.service.dto.category.CategorySummaryDto;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitSummaryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecruitDtoFactory {

    public static RecruitSummaryDto toRecruitSummary(Recruit recruit, Map<Long, List<CategoryMapper>> categoryMapperMap) {
        RecruitSummaryDto dto = new RecruitSummaryDto();

        dto.setId(recruit.getId());
        dto.setTitle(recruit.getTitle());
        dto.setState(recruit.getState());
        dto.setRecruitType(recruit.getGroupType());
        dto.setRecruitPeriod(recruit.getRecruitPeriod());
        dto.setViewCount(recruit.getViewCount());
        dto.setOnlineType(recruit.getOnlineType());
        dto.setCommentCount(recruit.getCommentCount());

        List<CategoryMapper> categoryMapperList = categoryMapperMap.get(recruit.getId());
        ArrayList<CategorySummaryDto> hashList = new ArrayList<>();
        ArrayList<CategorySummaryDto> techList = new ArrayList<>();
        if (categoryMapperList != null && !categoryMapperList.isEmpty()) {
            for (CategoryMapper categoryMapper : categoryMapperList) {
                CategorySummaryDto categorySummaryDto = CategoryDtoFactory.toCategorySummary(categoryMapper);
                if (CategoryType.HASH.equals(categoryMapper.getCategory().getType())) hashList.add(categorySummaryDto);
                else if (CategoryType.TECH_STACK.equals(categoryMapper.getCategory().getType())) techList.add(categorySummaryDto);
            }
        }
        dto.setHashList(hashList);
        dto.setTechList(techList);

        return dto;
    }
}
