package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.FriendState;
import jakarta.persistence.*;

public class Friend extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "FRIEND_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member toMember;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private FriendState state;
}
