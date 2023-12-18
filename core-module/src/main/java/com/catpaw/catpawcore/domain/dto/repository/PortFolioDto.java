package com.catpaw.catpawcore.domain.dto.repository;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class PortFolioDto {

    private final Long memberId;
    private final Long fileId;
    private final String absoluteDestination;
    private final String originalName;
    private final LocalDateTime created;
    private final LocalDateTime updated;

    public PortFolioDto(Long memberId, Long fileId, String absoluteDestination, String originalName, LocalDateTime created, LocalDateTime updated) {
        this.memberId = memberId;
        this.fileId = fileId;
        this.absoluteDestination = absoluteDestination;
        this.originalName = originalName;
        this.created = created;
        this.updated = updated;
    }
}
