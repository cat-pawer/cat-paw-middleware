package com.catpaw.catpawmiddleware.repository.condition;

import com.catpaw.catpawmiddleware.domain.eumns.OnlineType;
import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter @Setter
public class RecruitSearchCond {

    private Long recruitId;

    private Long groupId;

    private String title;

    private LocalDate recruitPeriod;

    private LocalDate startDate;

    private LocalDate endDate;

    private OnlineType onlineType;

    private RecruitState state;
}
