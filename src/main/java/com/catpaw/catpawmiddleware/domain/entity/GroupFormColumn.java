package com.catpaw.catpawmiddleware.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class GroupFormColumn extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "GROUP_FORM_COLUMN")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Groups groups;

    private String columnKey;

    private String columnValue;

}
