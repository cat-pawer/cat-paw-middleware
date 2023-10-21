package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.Scope;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Calendar extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "CALENDAR_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Groups groups;

    private String name;
    private String detail;
    private String content;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private Scope scope;
}
