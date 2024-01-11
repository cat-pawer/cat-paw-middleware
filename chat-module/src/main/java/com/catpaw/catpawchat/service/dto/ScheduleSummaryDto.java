package com.catpaw.catpawchat.service.dto;

import com.catpaw.catpawcore.domain.entity.ScheduleSummary;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class ScheduleSummaryDto {

    private Long id;

    private Long cMemberId;

    private Long statusCodeId;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime created;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updated;

    private Long createdBy;

    private Long updatedBy;

    public ScheduleSummaryDto() {
    }

    public ScheduleSummaryDto(ScheduleSummary scheduleSummary) {
        this.id = scheduleSummary.getId();
        if (scheduleSummary.getCMember() != null) this.cMemberId = scheduleSummary.getCMember().getId();
        this.statusCodeId = scheduleSummary.getStatusCodeId();
        this.title = scheduleSummary.getTitle();
        this.startDate = scheduleSummary.getStartDate();
        this.endDate = scheduleSummary.getEndDate();
        this.created = scheduleSummary.getCreated();
        this.updated = scheduleSummary.getUpdated();
        this.createdBy = scheduleSummary.getCreatedBy();
        this.updatedBy = scheduleSummary.getUpdatedBy();
    }
}
