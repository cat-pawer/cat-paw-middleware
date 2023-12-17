package com.catpaw.catpawcore.common.factory.dto;

import com.catpaw.catpawcore.domain.dto.service.group.GroupsDetailDto;
import com.catpaw.catpawcore.domain.dto.service.group.GroupsSummaryDto;
import com.catpaw.catpawcore.domain.entity.Groups;

public class GroupDtoFactory {

    public static GroupsDetailDto toGroupDetail(Groups groups) {
        GroupsDetailDto groupsDetailDto = new GroupsDetailDto();

        return groupsDetailDto;
    }

    public static GroupsSummaryDto toGroupSummary(Groups groups) {
        GroupsSummaryDto groupsSummaryDto = new GroupsSummaryDto();
        groupsSummaryDto.setId(groups.getId());
        groupsSummaryDto.setName(groups.getName());
        groupsSummaryDto.setDetail(groups.getDetail());
        groupsSummaryDto.setType(groups.getType());
        groupsSummaryDto.setState(groups.getState());
        groupsSummaryDto.setEndDate(groups.getEndDate());

        return groupsSummaryDto;
    }
}
