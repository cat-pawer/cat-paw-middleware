package com.catpaw.catpawmiddleware.controller.v1.request.comment;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(name = "댓글 추가 폼")
public class AddCommentForm {

    @NotNull
    @Schema(description = "대상 모집글 id")
    private Long recruitId;

    @NotBlank
    @Schema(description = "댓글 내용")
    private String content;
}
