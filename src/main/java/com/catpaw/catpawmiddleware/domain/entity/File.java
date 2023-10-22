package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class File extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "FILE_ID")
    private Long id;

    private Long targetId;

    private String targetUUID;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TargetType type;

    private String fileName;

    private String fileOriginalName;

}
