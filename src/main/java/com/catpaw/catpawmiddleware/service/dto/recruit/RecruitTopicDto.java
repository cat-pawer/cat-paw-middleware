package com.catpaw.catpawmiddleware.service.dto.recruit;

import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class RecruitTopicDto {

    private String topic;

    private LocalDate recruitPeriod;

    private RecruitState state;
}
