package com.catpaw.catpawcore.domain.dto.service.friend;

import com.catpaw.catpawcore.domain.eumns.FriendState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class FriendSummaryDto {

    private Long friendId;

    private Long memberId;

    private String name;

    private FriendState state;

    private LocalDateTime created;

    private LocalDateTime updated;
}
