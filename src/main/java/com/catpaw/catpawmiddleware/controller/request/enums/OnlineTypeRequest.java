package com.catpaw.catpawmiddleware.controller.request.enums;

import com.catpaw.catpawmiddleware.domain.eumns.GroupType;
import com.catpaw.catpawmiddleware.domain.eumns.OnlineType;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OnlineTypeRequest implements BaseEnumRequest<OnlineType> {

    ONLINE("online"),
    OFFLINE("offline"),
    COMPOSITE("composite");

    private final String code;

    @Override
    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public OnlineType toEnum() {
        if (ONLINE.name().equals(this.name())) return OnlineType.ONLINE;
        else if (OFFLINE.name().equals(this.name())) return OnlineType.OFFLINE;
        else if (COMPOSITE.name().equals(this.name())) return OnlineType.COMPOSITE;
        else throw new IllegalArgumentException();
    }
}
