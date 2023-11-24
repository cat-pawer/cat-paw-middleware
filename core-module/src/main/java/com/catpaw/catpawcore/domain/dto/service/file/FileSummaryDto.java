package com.catpaw.catpawcore.domain.dto.service.file;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileSummaryDto {

    private Long id;

    private String absoluteDestination;

    private String fileOriginalName;
}
