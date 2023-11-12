package com.catpaw.catpawmiddleware.controller.response.comment;

import com.catpaw.catpawmiddleware.controller.response.Result;
import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import com.catpaw.catpawmiddleware.service.dto.comment.CommentSummaryDto;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "댓글 리스트 조회")
public class CommentSummarySchema extends Result<CustomPageDto<CommentSummaryDto>> {

    @Schema(description = "응답 코드")
    private int code;

    @Schema(description = "응답 메세지")
    private String message;

    @Schema(description = "응답 데이터")
    private CustomPageDto<CommentSummaryDto> data;
}
