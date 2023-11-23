package com.catpaw.catpawmiddleware.service.dto.file;

import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileDetailDto {

    private Long id;

    private Long targetId;

    private String targetUUID;

    private TargetType type;

    private String absoluteDestination;

    private String fileOriginalName;

    private String fileKey;
}
