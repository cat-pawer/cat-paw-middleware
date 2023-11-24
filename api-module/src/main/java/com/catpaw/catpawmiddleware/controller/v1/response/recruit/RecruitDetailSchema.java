package com.catpaw.catpawmiddleware.controller.v1.response.recruit;

import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import com.catpaw.catpawcore.domain.dto.repository.RecruitDetailDto;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "모집 단건 조회")
public class RecruitDetailSchema extends Result<RecruitDetailDto> {

    @Schema(description = "응답 코드")
    private int code;

    @Schema(description = "응답 메세지")
    private String message;

    @Schema(description = "응답 데이터")
    private RecruitDetailDto data;
}
