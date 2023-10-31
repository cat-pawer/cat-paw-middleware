package com.catpaw.catpawmiddleware.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;

public class AddFriendForm {

    @Schema(description = "친구 요청 대상 id")
    private Long targetId;

    public Long getTargetId() {
        return this.targetId;
    }
}
