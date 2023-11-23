package com.catpaw.catpawcore.domain.entity;

import com.catpaw.catpawcore.domain.entity.base.SimpleBaseEntity;
import com.catpaw.catpawcore.domain.eumns.TargetType;
import com.catpaw.catpawcore.domain.eumns.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE log SET is_delete = 'Y' WHERE log_id = ?")
public class Log extends SimpleBaseEntity {

    @Id @GeneratedValue
    @Column(name = "LOG_ID")
    private Long id;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TargetType type;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private String body;

}
