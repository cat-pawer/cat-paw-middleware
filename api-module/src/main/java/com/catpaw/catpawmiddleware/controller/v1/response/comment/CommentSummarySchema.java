package com.catpaw.catpawmiddleware.controller.v1.response.comment;

import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import com.catpaw.catpawcore.domain.dto.service.CustomPageDto;
import com.catpaw.catpawcore.domain.dto.service.comment.CommentSummaryDto;
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
