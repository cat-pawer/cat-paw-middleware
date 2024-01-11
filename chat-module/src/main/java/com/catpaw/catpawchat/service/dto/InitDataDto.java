package com.catpaw.catpawchat.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class InitDataDto {

    private boolean isReady;

    private List<ScheduleDto> scheduleDtoList = new ArrayList<>();
}
