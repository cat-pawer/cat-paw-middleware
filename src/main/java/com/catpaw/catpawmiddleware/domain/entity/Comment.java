package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Comment extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "COMMENT_ID")
    private Long id;

    @OneToMany
    @JoinColumn(name = "COMMENT_ID")
    private List<Comment> commentList = new ArrayList<>();

    private Long targetId;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TargetType type;

    private String content;
}
