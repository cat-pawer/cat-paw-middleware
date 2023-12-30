package com.catpaw.catpawmiddleware.controller.v1.response.groups;

import com.catpaw.catpawcore.domain.dto.service.CustomPageDto;
import com.catpaw.catpawcore.domain.dto.service.group.GroupBoardSummaryDto;
import com.catpaw.catpawcore.domain.dto.service.group.GroupsSummaryDto;
import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "프로젝트 게시물 리스트 조회")
public class GroupsBoardSummarySchema extends Result<CustomPageDto<GroupBoardSummaryDto>> {

    @Schema(description = "응답 코드")
    private int code;

    @Schema(description = "응답 메세지")
    private String message;

    @Schema(description = "응답 데이터")
    private CustomPageDto<GroupBoardSummaryDto> data;
}
