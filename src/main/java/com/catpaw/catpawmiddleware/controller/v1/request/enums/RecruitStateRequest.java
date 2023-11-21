package com.catpaw.catpawmiddleware.controller.v1.request.enums;

import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitStateRequest implements BaseEnumRequest<RecruitState> {

    ACTIVE("active"),
    COMPLETE("complete");

    private final String code;

    @Override
    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public RecruitState toEnum() {
        if (ACTIVE.name().equals(this.name())) return RecruitState.ACTIVE;
        else if (COMPLETE.name().equals(this.name())) return RecruitState.COMPLETE;
        else throw new IllegalArgumentException();
    }

}
