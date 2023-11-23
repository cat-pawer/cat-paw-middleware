package com.catpaw.catpawmiddleware.service.dto.file;

import com.catpaw.catpawcore.domain.eumns.TargetType;
import com.catpaw.catpawcore.utils.LogUtils;
import org.springframework.util.Assert;

public record FileTarget(long targetId, TargetType targetType) {

    public FileTarget {
        Assert.notNull(targetType, LogUtils.notNullFormat("targetType"));
    }
}
