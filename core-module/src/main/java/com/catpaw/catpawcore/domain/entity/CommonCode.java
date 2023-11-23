package com.catpaw.catpawcore.domain.entity;

import com.catpaw.catpawcore.domain.entity.base.BaseEntity;
import com.catpaw.catpawcore.domain.eumns.TargetType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE common_code SET is_delete = 'Y' WHERE common_code_id = ?")
public class CommonCode extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "COMMON_CODE_ID")
    private Long id;

    public String name;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TargetType type;

}
