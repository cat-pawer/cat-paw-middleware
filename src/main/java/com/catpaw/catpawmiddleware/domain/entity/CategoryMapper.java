package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class CategoryMapper extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "CATEGORY__MAPPER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    private Long targetId;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TargetType type;

}