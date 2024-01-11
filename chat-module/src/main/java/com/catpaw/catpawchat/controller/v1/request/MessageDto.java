package com.catpaw.catpawchat.controller.v1.request;


import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter @Setter
public class MessageDto<T, ST> implements Serializable {

    @Serial
    private static final long serialVersionUID = 2804306926608218778L;

    private Long memberId;
    private Long groupId;
    private Long targetId;
    private MessageType messageType;
    private T data;
    private ST subData;
}
