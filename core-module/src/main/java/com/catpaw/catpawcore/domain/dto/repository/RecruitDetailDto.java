package com.catpaw.catpawcore.domain.dto.repository;

import com.catpaw.catpawcore.domain.entity.Recruit;
import com.catpaw.catpawcore.domain.eumns.GroupType;
import com.catpaw.catpawcore.domain.eumns.OnlineType;
import com.catpaw.catpawcore.domain.eumns.RecruitState;
import com.catpaw.catpawcore.domain.dto.service.category.CategorySummaryDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class RecruitDetailDto {

    private Long id;

    private String title;

    private String content;

    private String contact;

    private GroupType groupType;

    private LocalDate recruitPeriod;

    private Integer peopleNumber;

    private Integer expectDuration;

    private Integer viewCount;

    private Integer commentCount;

    private OnlineType onlineType;

    private RecruitState state;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Long createdBy;

    private Long updatedBy;

    private List<CategorySummaryDto> tagList = new ArrayList<>();

    private List<CategorySummaryDto> positionList = new ArrayList<>();

    private List<CategorySummaryDto> techList = new ArrayList<>();

    public void copyRecruit(Recruit recruit) {
        this.id = recruit.getId();
        this.title = recruit.getTitle();
        this.content = recruit.getContent();
        this.contact = recruit.getContact();
        this.groupType = recruit.getGroupType();
        this.recruitPeriod = recruit.getRecruitPeriod();
        this.peopleNumber = recruit.getPeopleNumber();
        this.expectDuration = recruit.getExpectDuration();
        this.viewCount = recruit.getViewCount();
        this.commentCount = recruit.getCommentCount();
        this.onlineType = recruit.getOnlineType();
        this.state = recruit.getState();
        this.created = recruit.getCreated();
        this.updated = recruit.getUpdated();
        this.createdBy = recruit.getCreatedBy();
        this.updatedBy = recruit.getUpdatedBy();
    }
}
