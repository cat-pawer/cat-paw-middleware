package com.catpaw.catpawchat.service;

import java.io.Serializable;

public class MessageDto<T> implements Serializable {

    private final Long memberId;

    private final Long targetId;

    private final T data;

    public MessageDto(Long memberId, Long targetId, T data) {
        this.memberId = memberId;
        this.targetId = targetId;
        this.data = data;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public T getData() {
        return data;
    }
}
