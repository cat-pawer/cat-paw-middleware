package com.catpaw.catpawcore.domain.dto.service.file;

import com.catpaw.catpawcore.domain.eumns.TargetType;
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
