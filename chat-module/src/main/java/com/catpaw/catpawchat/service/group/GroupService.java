package com.catpaw.catpawchat.service.group;


import com.catpaw.catpawchat.repository.group.GroupRepository;
import com.catpaw.catpawcore.common.factory.dto.GroupDtoFactory;
import com.catpaw.catpawcore.domain.dto.service.group.GroupSummaryDto;
import com.catpaw.catpawcore.domain.entity.Groups;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<GroupSummaryDto> getGroupListByMemberId(long memberId) {
        List<Groups> groupList = groupRepository.findGroupListByMemberId(memberId);

        return groupList.stream().map(GroupDtoFactory::toGroupSummary).toList();
    }
}
