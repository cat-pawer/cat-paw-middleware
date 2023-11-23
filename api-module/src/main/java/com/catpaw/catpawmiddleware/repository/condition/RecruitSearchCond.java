package com.catpaw.catpawmiddleware.repository.condition;

import com.catpaw.catpawcore.domain.eumns.GroupType;
import com.catpaw.catpawcore.domain.eumns.OnlineType;
import com.catpaw.catpawcore.domain.eumns.RecruitState;
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
