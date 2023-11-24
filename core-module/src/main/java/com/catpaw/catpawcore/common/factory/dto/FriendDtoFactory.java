package com.catpaw.catpawcore.common.factory.dto;

import com.catpaw.catpawcore.domain.entity.Friend;
import com.catpaw.catpawcore.domain.dto.service.friend.FriendSummaryDto;

public class FriendDtoFactory {


    public static FriendSummaryDto toFriendSummary(Friend friend) {
        FriendSummaryDto dto = new FriendSummaryDto();

        dto.setFriendId(friend.getId());
        dto.setMemberId(friend.getToMember().getId());
        dto.setName(friend.getToMember().getName());
        dto.setState(friend.getState());
        dto.setCreated(friend.getCreated());
        dto.setUpdated(friend.getUpdated());

        return dto;
    }
}
