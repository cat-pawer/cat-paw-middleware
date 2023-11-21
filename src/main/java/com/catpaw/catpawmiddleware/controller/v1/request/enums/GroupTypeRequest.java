package com.catpaw.catpawmiddleware.controller.v1.request.enums;

import com.catpaw.catpawmiddleware.domain.eumns.GroupType;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GroupTypeRequest implements BaseEnumRequest<GroupType> {

    PROJECT("project"),
    STUDY("study");

    private final String code;

    @Override
    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public GroupType toEnum() {
        if (PROJECT.name().equals(this.name())) return GroupType.PROJECT;
        else if (STUDY.name().equals(this.name())) return GroupType.STUDY;
        else throw new IllegalArgumentException();
    }
}
