package com.catpaw.catpawchat.service.group;


import com.catpaw.catpawchat.repository.group.GroupRepository;
import com.catpaw.catpawchat.service.dto.GroupMemberDto;
import com.catpaw.catpawchat.service.dto.ScheduleDto;
import com.catpaw.catpawchat.service.dto.ScheduleSummaryDto;
import com.catpaw.catpawcore.common.factory.dto.GroupDtoFactory;
import com.catpaw.catpawcore.domain.dto.service.group.GroupsSummaryDto;
import com.catpaw.catpawcore.domain.entity.GroupMember;
import com.catpaw.catpawcore.domain.entity.Groups;
import com.catpaw.catpawcore.domain.entity.ScheduleSummary;
import com.catpaw.catpawcore.utils.LogUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<ScheduleDto> getScheduleSummaryList(long groupId) {
        return groupRepository.findScheduleSummaryByGroupId(groupId);
    }

    public List<GroupsSummaryDto> getGroupListByMemberId(long memberId) {
        List<Groups> groupList = groupRepository.findGroupListByMemberId(memberId);

        return groupList.stream().map(GroupDtoFactory::toGroupSummary).toList();
    }

    public List<GroupMember> getGroupMemberList(long groupId) {
        return groupRepository.findGroupMemberList(groupId);
    }

    public List<GroupMemberDto> getGroupMemberDtoList(long groupId) {
        List<GroupMember> groupMemberList = groupRepository.findGroupMemberList(groupId);

        return groupMemberList.stream().map(groupMember -> {
            GroupMemberDto groupMemberDto = new GroupMemberDto();
            groupMemberDto.setId(groupMember.getId());
            groupMemberDto.setMemberId(groupMember.getMember().getId());
            groupMemberDto.setMemberName(groupMember.getMember().getName());
            groupMemberDto.setAuth(groupMember.getAuth());
            groupMemberDto.setCreated(groupMember.getCreated());
            groupMemberDto.setUpdated(groupMember.getUpdated());
            return groupMemberDto;
        }).toList();
    }

    public Optional<Groups> findById(Long groupId) {
        Assert.notNull(groupId, LogUtils.notNullFormat("groupId"));

        return groupRepository.findById(groupId);
    }

    public boolean checkAuthentication(long memberId, long groupId) {
        return groupRepository.findMemberOfGroups(memberId, groupId).isPresent();
    }
}
