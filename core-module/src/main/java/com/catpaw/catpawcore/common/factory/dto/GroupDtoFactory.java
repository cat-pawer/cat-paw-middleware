package com.catpaw.catpawcore.common.factory.dto;

import com.catpaw.catpawcore.domain.dto.service.group.GroupMemberSummaryDto;
import com.catpaw.catpawcore.domain.dto.service.group.GroupsDetailDto;
import com.catpaw.catpawcore.domain.dto.service.group.GroupsSummaryDto;
import com.catpaw.catpawcore.domain.entity.GroupMember;
import com.catpaw.catpawcore.domain.entity.Groups;
import com.catpaw.catpawcore.domain.eumns.GroupState;
import com.catpaw.catpawcore.domain.eumns.GroupType;

import java.time.LocalDate;

public class GroupDtoFactory {

    public static GroupsDetailDto toGroupDetail(Groups groups) {
        GroupsDetailDto groupsDetailDto = new GroupsDetailDto();
        groupsDetailDto.setName(groups.getName());
        groupsDetailDto.setDetail(groups.getDetail());
        groupsDetailDto.setState(groups.getState());
        groupsDetailDto.setType(groups.getType());
        groupsDetailDto.setEndDate(groups.getEndDate());
        if (groups.getMemberList() != null) {
            groupsDetailDto.setMemberList(groups.getMemberList().stream()
                    .map(GroupDtoFactory::toGroupMemberSummary)
                    .toList());
        }
        groupsDetailDto.setCreated(groups.getCreated());
        groupsDetailDto.setUpdated(groups.getUpdated());

        return groupsDetailDto;
    }

    public static GroupMemberSummaryDto toGroupMemberSummary(GroupMember groupMember) {
        GroupMemberSummaryDto groupMemberSummaryDto = new GroupMemberSummaryDto();
        groupMemberSummaryDto.setId(groupMember.getId());
        groupMemberSummaryDto.setMemberId(groupMember.getMember().getId());
        groupMemberSummaryDto.setName(groupMember.getMember().getName());
        groupMemberSummaryDto.setAuth(groupMember.getAuth());
        groupMemberSummaryDto.setState(groupMember.getState());

        return groupMemberSummaryDto;
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
