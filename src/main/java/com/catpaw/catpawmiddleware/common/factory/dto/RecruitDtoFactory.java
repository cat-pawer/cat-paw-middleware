package com.catpaw.catpawmiddleware.common.factory.dto;

import com.catpaw.catpawmiddleware.domain.entity.Category;
import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.domain.entity.Tag;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitSummaryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecruitDtoFactory {

    public static RecruitSummaryDto toRecruitSummary(Recruit recruit, Map<Long, List<Tag>> tagMapper) {
        RecruitSummaryDto dto = new RecruitSummaryDto();

        dto.setId(recruit.getId());
        dto.setTitle(recruit.getTitle());
        dto.setState(recruit.getState());
        dto.setRecruitType(recruit.getGroupType());
        dto.setRecruitPeriod(recruit.getRecruitPeriod());
        dto.setViewCount(recruit.getViewCount());
        dto.setOnlineType(recruit.getOnlineType());
        dto.setCommentCount(recruit.getCommentCount());
        dto.setTagList(tagMapper.getOrDefault(recruit.getId(), new ArrayList<>()));

        return dto;
    }


    public static RecruitSummaryDto toRecruitSummary(Recruit recruit, Map<Long, List<Tag>> tagMapper, Map<Long, List<Category>> categoryMapper) {
        RecruitSummaryDto dto = new RecruitSummaryDto();

        dto.setId(recruit.getId());
        dto.setTitle(recruit.getTitle());
        dto.setState(recruit.getState());
        dto.setRecruitType(recruit.getGroupType());
        dto.setRecruitPeriod(recruit.getRecruitPeriod());
        dto.setViewCount(recruit.getViewCount());
        dto.setOnlineType(recruit.getOnlineType());
        dto.setCommentCount(recruit.getCommentCount());
        dto.setTagList(tagMapper.getOrDefault(recruit.getId(), new ArrayList<>()));
        dto.setCategoryList(categoryMapper.getOrDefault(recruit.getId(), new ArrayList<>()));

        return dto;
    }
}
