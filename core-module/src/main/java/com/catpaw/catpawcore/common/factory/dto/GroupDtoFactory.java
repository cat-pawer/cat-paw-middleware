package com.catpaw.catpawcore.common.factory.dto;

import com.catpaw.catpawcore.domain.dto.service.group.GroupDetailDto;
import com.catpaw.catpawcore.domain.dto.service.group.GroupSummaryDto;
import com.catpaw.catpawcore.domain.entity.Groups;

public class GroupDtoFactory {

    public static GroupDetailDto toGroupDetail(Groups groups) {
        GroupDetailDto groupDetailDto = new GroupDetailDto();

        return groupDetailDto;
    }

    public static GroupSummaryDto toGroupSummary(Groups groups) {
        GroupSummaryDto groupSummaryDto = new GroupSummaryDto();

        return groupSummaryDto;
    }
}
