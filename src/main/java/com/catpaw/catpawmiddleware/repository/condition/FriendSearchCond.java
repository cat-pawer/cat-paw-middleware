package com.catpaw.catpawmiddleware.repository.condition;

import com.catpaw.catpawmiddleware.domain.eumns.FriendState;
import lombok.Getter;


@Getter
public class FriendSearchCond {

    private Long memberId;
    private FriendState state;
    private String name;

    public FriendSearchCond(Long memberId, FriendState state, String name) {
        this.memberId = memberId;
        this.state = state;
        this.name = name;
    }
}
