package com.catpaw.catpawmiddleware.repository.dto;

import com.catpaw.catpawmiddleware.domain.entity.Member;
import com.catpaw.catpawmiddleware.domain.eumns.FriendState;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FriendSummaryDto {

    private Long id;

    private Member fromMember;

    private Member toMember;

    private FriendState state;

    private LocalDateTime updated;

    public FriendSummaryDto(Long id, Member fromMember, Member toMember, FriendState state, LocalDateTime updated) {
        this.id = id;
        this.fromMember = fromMember;
        this.toMember = toMember;
        this.state = state;
        this.updated = updated;
    }
}
