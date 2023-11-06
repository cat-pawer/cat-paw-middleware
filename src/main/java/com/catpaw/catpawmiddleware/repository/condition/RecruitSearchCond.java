package com.catpaw.catpawmiddleware.repository.condition;

import com.catpaw.catpawmiddleware.domain.eumns.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter @Setter
public class RecruitSearchCond {

    private String searchValue;

    private String title;

    private LocalDate recruitPeriod;

    private GroupType recruitType;

    private OnlineType onlineType;

    private RecruitState state;

    private List<Long> categoryIdList;
}
