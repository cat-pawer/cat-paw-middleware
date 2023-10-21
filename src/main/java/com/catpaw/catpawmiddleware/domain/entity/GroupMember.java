package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.Auth;
import com.catpaw.catpawmiddleware.domain.eumns.GroupMemberState;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class GroupMember extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "GROUP_MEMBER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Groups groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POSITION_ID",
            referencedColumnName = "CATEGORY_ID",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Category positionId;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private Auth auth;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private GroupMemberState state;
}
