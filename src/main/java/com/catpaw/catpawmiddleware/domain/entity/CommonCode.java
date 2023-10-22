package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class CommonCode extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "COMMON_CODE_ID")
    private Long id;

    public String name;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TargetType type;

}
