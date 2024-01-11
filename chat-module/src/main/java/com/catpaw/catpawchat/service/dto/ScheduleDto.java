package com.catpaw.catpawchat.service.dto;

import com.catpaw.catpawcore.domain.entity.Schedule;
import com.catpaw.catpawcore.domain.entity.ScheduleSummary;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ScheduleDto {

    private Long id;

    private String title;

    private String body;

    private List<ScheduleSummaryDto> scheduleSummaryDtoList = new ArrayList<>();

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime created;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updated;

    private Long createdBy;

    private Long updatedBy;

    public void addScheduleSummaryDto(ScheduleSummaryDto scheduleSummaryDto) {
        this.scheduleSummaryDtoList.add(scheduleSummaryDto);
    }

    public ScheduleDto() {
    }

    public ScheduleDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.body = schedule.getBody();
        this.created = schedule.getCreated();
        this.updated = schedule.getUpdated();
        this.createdBy = schedule.getCreatedBy();
        this.updatedBy = schedule.getUpdatedBy();
    }

    public ScheduleDto(ScheduleSummary scheduleSummary) {
        this.id = scheduleSummary.getSchedule().getId();
        this.title = scheduleSummary.getSchedule().getTitle();
        this.body = scheduleSummary.getSchedule().getBody();
        this.created = scheduleSummary.getSchedule().getCreated();
        this.updated = scheduleSummary.getSchedule().getUpdated();
        this.createdBy = scheduleSummary.getSchedule().getCreatedBy();
        this.updatedBy = scheduleSummary.getSchedule().getUpdatedBy();
    }
}
