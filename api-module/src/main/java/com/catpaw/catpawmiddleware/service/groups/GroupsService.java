package com.catpaw.catpawmiddleware.service.groups;

import com.catpaw.catpawcore.common.factory.dto.CustomPageDtoFactory;
import com.catpaw.catpawcore.common.factory.dto.GroupDtoFactory;
import com.catpaw.catpawcore.common.factory.dto.RecruitDtoFactory;
import com.catpaw.catpawcore.domain.dto.service.CustomPageDto;
import com.catpaw.catpawcore.domain.dto.service.group.GroupsDetailDto;
import com.catpaw.catpawcore.domain.dto.service.group.GroupsSummaryDto;
import com.catpaw.catpawcore.domain.entity.GroupMember;
import com.catpaw.catpawcore.domain.entity.Groups;
import com.catpaw.catpawcore.exception.custom.DataNotFoundException;
import com.catpaw.catpawcore.exception.custom.ForbiddenException;
import com.catpaw.catpawcore.utils.LogUtils;
import com.catpaw.catpawmiddleware.repository.groups.GroupsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GroupsService {

    private final GroupsRepository groupsRepository;

    public CustomPageDto<GroupsSummaryDto> getMyGroupsSummary(long memberId, Pageable pageable) {
        Assert.notNull(pageable, LogUtils.notNullFormat("pageable"));

        return CustomPageDtoFactory.createCustomPageDto(
                groupsRepository.findSlicedMyGroups(memberId, pageable)
                        .map(GroupDtoFactory::toGroupSummary));
    }


    public CustomPageDto<GroupsSummaryDto> getOtherGroupsSummary(long memberId, Pageable pageable) {
        Assert.notNull(pageable, LogUtils.notNullFormat("pageable"));

        return CustomPageDtoFactory.createCustomPageDto(
                groupsRepository.findSlicedOtherGroups(memberId, pageable)
                        .map(GroupDtoFactory::toGroupSummary));
    }

    public GroupsDetailDto getGroupDetail(long groupId, long memberId) {
        if (!this.checkGroupAuthentication(groupId, memberId)) {
            throw new ForbiddenException();
        }

        Groups groups = groupsRepository.findGroupsWithMember(groupId)
                .orElseThrow(() -> { throw new DataNotFoundException(); });

        return GroupDtoFactory.toGroupDetail(groups);
    }

    public boolean checkGroupAuthentication(long groupId, long memberId) {
        return groupsRepository.findGroupMemberByGroupIdAndMemberId(memberId, groupId)
                .isPresent();
    }
}
