package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class BookMark extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "BOOKMARK_ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private Long targetId;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TargetType type;
}

