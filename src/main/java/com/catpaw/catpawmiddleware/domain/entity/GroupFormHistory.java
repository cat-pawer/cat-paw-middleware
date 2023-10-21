package com.catpaw.catpawmiddleware.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class GroupFormHistory extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "GROUP_FORM_HISTORY")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Groups groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_MEMBER_ID")
    private GroupMember groupMember;

    private String content;
}
