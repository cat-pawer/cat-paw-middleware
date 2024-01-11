package com.catpaw.catpawcore.service.dto;

import com.catpaw.catpawcore.domain.entity.CommonCode;
import com.catpaw.catpawcore.domain.eumns.CodeType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommonCodeDto {

    private Long id;

    public String name;

    private CodeType type;

    private LocalDateTime created;

    private LocalDateTime updated;

    public CommonCodeDto() {
    }

    public CommonCodeDto(CommonCode commonCode) {
        this.id = commonCode.getId();
        this.name = commonCode.getName();
        this.type = commonCode.getType();
        this.created = commonCode.getCreated();
        this.updated = commonCode.getUpdated();
    }
}
