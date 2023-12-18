package com.catpaw.catpawcore.domain.dto.service.file;


import java.time.LocalDateTime;

public record FileSummaryDto(Long id, Long fileId, String absoluteDestination, String fileOriginalName,
                             LocalDateTime created, LocalDateTime updated) {
}
