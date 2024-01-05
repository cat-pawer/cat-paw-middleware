package com.catpaw.catpawmiddleware.controller.v1.request.friend;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "친구 추가 폼")
public class AddFriendRequest {

    @Schema(description = "친구 요청 대상 id")
    private Long targetId;
}
