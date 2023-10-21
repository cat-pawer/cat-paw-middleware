package com.catpaw.catpawmiddleware.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class GroupBoard extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "GROUP_BOARD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Groups groups;

    private String title;

    private String content;

    private Long likeCount;
}
