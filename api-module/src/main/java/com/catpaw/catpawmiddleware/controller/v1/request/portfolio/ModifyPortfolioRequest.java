package com.catpaw.catpawmiddleware.controller.v1.request.portfolio;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "메인 포트폴리오 변경 폼")
public record ModifyPortfolioRequest(@NotNull @Schema(description = "포트폴리오 고유번호") Long portfolioId) {

    public ModifyPortfolioRequest(Long portfolioId) {
        this.portfolioId = portfolioId;
    }
}
