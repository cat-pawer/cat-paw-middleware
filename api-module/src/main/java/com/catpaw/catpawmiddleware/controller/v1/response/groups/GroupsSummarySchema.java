package com.catpaw.catpawmiddleware.controller.v1.response.groups;

import com.catpaw.catpawcore.domain.dto.service.CustomPageDto;
import com.catpaw.catpawcore.domain.dto.service.group.GroupsSummaryDto;
import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "사용자 프로젝트 및 스터디 조회")
public class GroupsSummarySchema extends Result<GroupsSummaryDto> {

    @Schema(description = "응답 코드")
    private int code;

    @Schema(description = "응답 메세지")
    private String message;

    @Schema(description = "응답 데이터")
    private CustomPageDto<GroupsSummaryDto> data;
}
