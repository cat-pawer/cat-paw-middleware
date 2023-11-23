package com.catpaw.catpawmiddleware.controller.v1.response.recruit;

import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitSummaryDto;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "모집 리스트 조회")
public class RecruitSummarySchema extends Result<CustomPageDto<RecruitSummaryDto>> {

    @Schema(description = "응답 코드")
    private int code;

    @Schema(description = "응답 메세지")
    private String message;

    @Schema(description = "응답 데이터")
    private CustomPageDto<RecruitSummaryDto> data;
}
