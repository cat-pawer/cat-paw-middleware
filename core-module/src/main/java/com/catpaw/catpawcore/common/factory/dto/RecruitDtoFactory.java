package com.catpaw.catpawcore.common.factory.dto;

import com.catpaw.catpawcore.domain.entity.CategoryMapper;
import com.catpaw.catpawcore.domain.entity.Recruit;
import com.catpaw.catpawcore.domain.eumns.CategoryType;
import com.catpaw.catpawcore.domain.dto.service.category.CategorySummaryDto;
import com.catpaw.catpawcore.domain.dto.service.recruit.RecruitSummaryDto;

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
