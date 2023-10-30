package com.catpaw.catpawmiddleware.service.dto;

import com.catpaw.catpawmiddleware.domain.eumns.FriendState;
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
