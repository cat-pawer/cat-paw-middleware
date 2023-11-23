package com.catpaw.catpawmiddleware.service.dto.recruit;

import com.catpaw.catpawcore.domain.eumns.GroupType;
import com.catpaw.catpawcore.domain.eumns.OnlineType;
import com.catpaw.catpawcore.domain.eumns.RecruitState;
import com.catpaw.catpawmiddleware.service.dto.category.CategorySummaryDto;
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

    private List<CategorySummaryDto> hashList = new ArrayList<>();

    private List<CategorySummaryDto> techList = new ArrayList<>();
}
