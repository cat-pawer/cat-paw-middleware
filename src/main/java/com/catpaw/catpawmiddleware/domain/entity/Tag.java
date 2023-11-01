package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.TagType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Tag extends SimpleBaseEntity {

    @Id @GeneratedValue
    @Column(name = "TAG_ID")
    private Long id;

    private String name;

    private TagType type;
}
