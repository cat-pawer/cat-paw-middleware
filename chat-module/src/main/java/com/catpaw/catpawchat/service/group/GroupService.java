package com.catpaw.catpawchat.service.group;


import com.catpaw.catpawchat.repository.group.ChatGroupRepository;
import com.catpaw.catpawchat.service.dto.GroupMemberDto;
import com.catpaw.catpawchat.service.dto.ScheduleDto;
import com.catpaw.catpawcore.common.factory.dto.GroupDtoFactory;
import com.catpaw.catpawcore.domain.dto.service.group.GroupsSummaryDto;
import com.catpaw.catpawcore.domain.entity.GroupMember;
import com.catpaw.catpawcore.domain.entity.Groups;
import com.catpaw.catpawcore.utils.LogUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final ChatGroupRepository chatGroupRepository;

    public GroupService(ChatGroupRepository chatGroupRepository) {
        this.chatGroupRepository = chatGroupRepository;
    }

    public List<ScheduleDto> getScheduleSummaryList(long groupId) {
        return chatGroupRepository.findScheduleSummaryByGroupId(groupId);
    }

    public List<GroupsSummaryDto> getGroupListByMemberId(long memberId) {
        List<Groups> groupList = chatGroupRepository.findGroupListByMemberId(memberId);

        return groupList.stream().map(GroupDtoFactory::toGroupSummary).toList();
    }

    public List<GroupMember> getGroupMemberList(long groupId) {
        return chatGroupRepository.findGroupMemberList(groupId);
    }

    public List<GroupMemberDto> getGroupMemberDtoList(long groupId) {
        List<GroupMember> groupMemberList = chatGroupRepository.findGroupMemberList(groupId);

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

        return chatGroupRepository.findById(groupId);
    }

    public boolean checkAuthentication(long memberId, long groupId) {
        return chatGroupRepository.findMemberOfGroups(memberId, groupId).isPresent();
    }
}
