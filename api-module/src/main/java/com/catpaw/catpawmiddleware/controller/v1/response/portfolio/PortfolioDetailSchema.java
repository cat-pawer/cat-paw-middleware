package com.catpaw.catpawmiddleware.controller.v1.response.portfolio;

import com.catpaw.catpawcore.domain.dto.repository.PortFolioDto;
import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "포트폴리오 조회")
public class PortfolioDetailSchema extends Result<PortFolioDto> {

    @Schema(description = "응답 코드")
    private int code;

    @Schema(description = "응답 메세지")
    private String message;

    @Schema(description = "응답 데이터")
    private PortFolioDto data;
}
