package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.entity.base.BaseEntity;
import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE file SET is_delete = 'Y' WHERE file_id = ?")
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
