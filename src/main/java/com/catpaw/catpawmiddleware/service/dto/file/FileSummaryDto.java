package com.catpaw.catpawmiddleware.service.dto.file;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileSummaryDto {

    private Long id;

    private String absoluteDestination;

    private String fileOriginalName;
}
