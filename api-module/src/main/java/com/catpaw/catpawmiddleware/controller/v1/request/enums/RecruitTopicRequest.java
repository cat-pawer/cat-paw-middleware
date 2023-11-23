package com.catpaw.catpawmiddleware.controller.v1.request.enums;


import com.catpaw.catpawcore.domain.eumns.RecruitTopic;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitTopicRequest implements BaseEnumRequest<RecruitTopic> {

    DEADLINE("deadLine"),
    IS_NEW("isNew");

    private final String code;

    public String getValue() {
        return code;
    }

    @Override
    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public RecruitTopic toEnum() {
        if (RecruitTopic.DEADLINE.name().equals(this.name())) return RecruitTopic.DEADLINE;
        else if (RecruitTopic.IS_NEW.name().equals(this.name())) return RecruitTopic.IS_NEW;
        else throw new IllegalArgumentException();
    }
}
