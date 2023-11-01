package com.catpaw.catpawmiddleware.service.dto.recruit;

import com.catpaw.catpawmiddleware.domain.entity.Category;
import com.catpaw.catpawmiddleware.domain.entity.Tag;
import com.catpaw.catpawmiddleware.domain.eumns.GroupType;
import com.catpaw.catpawmiddleware.domain.eumns.OnlineType;
import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class RecruitSummaryDto {

    private Long id;

    private String title;

    private LocalDate recruitPeriod;

    private Integer viewCount;

    private Integer commentCount;

    private GroupType recruitType;

    private OnlineType onlineType;

    private RecruitState state;

    private List<Tag> tagList = new ArrayList<>();

    private List<Category> categoryList = new ArrayList<>();
}
