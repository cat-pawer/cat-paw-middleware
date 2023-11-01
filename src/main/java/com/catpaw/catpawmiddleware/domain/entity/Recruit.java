package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.GroupType;
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

    @Column(columnDefinition = "TEXT")
    private String content;

    private String contact;

    private GroupType groupType;

    private LocalDate recruitPeriod;

    private Integer peopleNumber;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer viewCount;

    private Integer commentCount;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private OnlineType onlineType;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private RecruitState state;
}
