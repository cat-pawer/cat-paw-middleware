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
public class FileMaster extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "FILE_MASTER_ID")
    private Long id;

    private Long targetId;

    private String targetUUID;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TargetType type;

    private String absoluteDestination;

    private String fileOriginalName;

    public FileMaster() {}

    public FileMaster(Long targetId, TargetType type, String absoluteDestination, String fileOriginalName) {
        this.targetId = targetId;
        this.type = type;
        this.absoluteDestination = absoluteDestination;
        this.fileOriginalName = fileOriginalName;
    }

    public FileMaster(String targetUUID, TargetType type, String absoluteDestination, String fileOriginalName) {
        this.targetUUID = targetUUID;
        this.type = type;
        this.absoluteDestination = absoluteDestination;
        this.fileOriginalName = fileOriginalName;
    }
}
