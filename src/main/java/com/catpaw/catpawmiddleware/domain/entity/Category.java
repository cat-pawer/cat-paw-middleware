package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.CategoryType;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Category extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    private String name;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private CategoryType type;

}
