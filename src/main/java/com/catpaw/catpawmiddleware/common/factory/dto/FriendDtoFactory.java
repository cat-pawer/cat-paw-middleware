package com.catpaw.catpawmiddleware.common.factory.dto;

import com.catpaw.catpawmiddleware.domain.entity.Friend;
import com.catpaw.catpawmiddleware.service.friend.FriendSummaryDto;

public class FriendDtoFactory {


    public static FriendSummaryDto toFriendSummary(Friend friend) {
        FriendSummaryDto dto = new FriendSummaryDto();
        dto.setFriendId(friend.getId());
        dto.setMemberId(friend.getToMember().getId());
        dto.setNickname(friend.getToMember().getNickname());
        dto.setState(friend.getState());
        dto.setCreated(friend.getCreated());
        dto.setUpdated(friend.getUpdated());
        return dto;
    }
}
