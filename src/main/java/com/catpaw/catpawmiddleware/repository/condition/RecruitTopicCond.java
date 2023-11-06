package com.catpaw.catpawmiddleware.repository.condition;

import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class RecruitTopicCond {

    private String topic;

    private LocalDate recruitPeriod;

    private LocalDateTime limitPeriod;

    private RecruitState state;
}
