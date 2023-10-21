package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.OnlineType;
import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
public class Recruit extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "RECRUIT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Groups groups;

    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private OnlineType onlineType;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private RecruitState state;

}
