package com.catpaw.catpawchat.controller.v1.request;


import java.io.Serializable;

public record MessageDto(Long memberId, Long targetId, MessageType messageType, String data, String subData) implements Serializable {

}
