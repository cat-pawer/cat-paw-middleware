package com.catpaw.catpawmiddleware.service.dto.recruit;

import com.catpaw.catpawcore.domain.eumns.RecruitState;
import com.catpaw.catpawcore.domain.eumns.RecruitTopic;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class RecruitTopicDto {

    private RecruitTopic topic;

    private LocalDate recruitPeriod;

    private RecruitState state;
}