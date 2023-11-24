package com.catpaw.catpawcore.domain.dto.service.friend;

import com.catpaw.catpawcore.domain.eumns.FriendState;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FriendSearchDto {

    private Long memberId;

    private Boolean myRequest;

    private Boolean otherRequest;

    private FriendState state;

    private String name;
}
