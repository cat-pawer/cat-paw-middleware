package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import com.catpaw.catpawmiddleware.domain.eumns.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Log extends BaseEntity {

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
