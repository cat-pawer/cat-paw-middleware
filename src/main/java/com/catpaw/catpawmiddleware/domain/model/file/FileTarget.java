package com.catpaw.catpawmiddleware.domain.model.file;

import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class FileTarget {

    private final Long targetId;

    private final TargetType targetType;

    public FileTarget(Long targetId, TargetType targetType) {
        this.targetId = targetId;
        this.targetType = targetType;
    }
}
