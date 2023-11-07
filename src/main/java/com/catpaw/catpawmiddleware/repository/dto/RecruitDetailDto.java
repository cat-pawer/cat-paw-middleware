package com.catpaw.catpawmiddleware.repository.dto;

import com.catpaw.catpawmiddleware.domain.entity.Groups;
import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.domain.eumns.GroupType;
import com.catpaw.catpawmiddleware.domain.eumns.OnlineType;
import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import com.catpaw.catpawmiddleware.service.dto.category.CategorySummaryDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class RecruitDetailDto {

    private Long id;

    private Groups groups;

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

    private List<CategorySummaryDto> tagList;

    private List<CategorySummaryDto> positionList;

    private List<CategorySummaryDto> techList;

    public RecruitDetailDto() {
        this.tagList = new ArrayList<>();
        this.positionList = new ArrayList<>();
        this.techList = new ArrayList<>();
    }

    public void setRecruit(Recruit recruit) {
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
    }
}
