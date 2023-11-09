package com.catpaw.catpawmiddleware.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "친구 추가 폼")
public class AddFriendForm {

    @Schema(description = "친구 요청 대상 id")
    private Long targetId;
}
