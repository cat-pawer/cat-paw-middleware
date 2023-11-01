package com.catpaw.catpawmiddleware.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class TagRecruit extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "TAG_RECRUIT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECRUIT_ID")
    private Recruit recruit;
}
