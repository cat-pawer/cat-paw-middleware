package com.catpaw.catpawmiddleware.repository.condition;

import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class RecruitTopicCond {

    private String topic;

    private LocalDate recruitPeriod;

    private RecruitState state;
}
