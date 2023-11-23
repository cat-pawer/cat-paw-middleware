package com.catpaw.catpawmiddleware.repository.condition;

import com.catpaw.catpawcore.domain.eumns.RecruitState;
import com.catpaw.catpawcore.domain.eumns.RecruitTopic;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class RecruitTopicCond {

    private RecruitTopic topic;

    private LocalDate recruitPeriod;

    private LocalDateTime limitPeriod;

    private RecruitState state;
}
