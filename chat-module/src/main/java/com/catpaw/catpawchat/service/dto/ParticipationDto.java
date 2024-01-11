package com.catpaw.catpawchat.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class ParticipationDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 8922118905241403044L;

    private Long memberId;

    private boolean isReady;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastAccessDate;

    public ParticipationDto() {}

    public ParticipationDto(Long memberId, LocalDateTime lastAccessDate) {
        this.memberId = memberId;
        this.lastAccessDate = lastAccessDate;
        this.isReady = false;
    }

    public void setLastAccessDate(LocalDateTime lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
