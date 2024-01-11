package com.catpaw.catpawmiddleware.controller.v1.portfolio;

import com.catpaw.catpawcore.common.resolver.annotation.LoginId;
import com.catpaw.catpawcore.domain.dto.repository.PortFolioDto;
import com.catpaw.catpawcore.domain.eumns.ResponseCode;
import com.catpaw.catpawmiddleware.controller.v1.request.portfolio.ModifyPortfolioRequest;
import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import com.catpaw.catpawmiddleware.controller.v1.response.portfolio.PortfolioDetailSchema;
import com.catpaw.catpawmiddleware.controller.v1.response.portfolio.PortfolioSummarySchema;
import com.catpaw.catpawcore.service.member.MemberService;
import com.catpaw.catpawmiddleware.service.portfolio.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "포토폴리오", description = "포토폴리오 도메인 API")
@SecurityRequirement(name = "bearer-token")
@RestController
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {

    private final MemberService memberService;
    private final PortfolioService portfolioService;

    public PortfolioController(MemberService memberService, PortfolioService portfolioService) {
        this.memberService = memberService;
        this.portfolioService = portfolioService;
    }

    @Operation(
            summary = "포트폴리오 조회",
            description = "사용자 포트폴리오 조회",
            tags = { "Get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "정상", content = { @Content(schema = @Schema(implementation = PortfolioSummarySchema.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",  description = "존재하지 않는 사용자", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401",  description = "인증되지 않은 사용자", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",  description = "메인 포트폴리오 존재하지 않음", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")})})
    @GetMapping("/summary")
    public ResponseEntity<Result<List<PortFolioDto>>> portfolioSummary(@Parameter(hidden = true) @LoginId Optional<Long> idHolder) {
        List<PortFolioDto> portfolioList = portfolioService.getPortfolioList(memberService.checkAndGetMemberId(idHolder));

        return ResponseEntity
                .ok()
                .body(Result.createSingleResult(ResponseCode.SUCCESS.getCode(), null, portfolioList));
    }

    @Operation(
            summary = "메인 포트폴리오 조회",
            description = "사용자 메인 포트폴리오 조회",
            tags = { "Get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "정상", content = { @Content(schema = @Schema(implementation = PortfolioDetailSchema.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",  description = "존재하지 않는 사용자", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401",  description = "인증되지 않은 사용자", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",  description = "메인 포트폴리오 존재하지 않음", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")})})
    @GetMapping("/main")
    public ResponseEntity<Result<PortFolioDto>> mainPortfolio(@Parameter(hidden = true) @LoginId Optional<Long> idHolder) {
        PortFolioDto mainPortfolio = portfolioService.getMainPortfolio(memberService.checkAndGetMemberId(idHolder));

        return ResponseEntity
                .ok()
                .body(Result.createSingleResult(ResponseCode.SUCCESS.getCode(), null, mainPortfolio));
    }

    @Operation(
            summary = "메인 포트폴리오 수정",
            description = "사용자 메인 포트폴리오 수정",
            tags = { "Patch" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "정상", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",  description = "존재하지 않는 사용자", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401",  description = "인증되지 않은 사용자", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",  description = "메인 포트폴리오 존재하지 않음", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")})})
    @PatchMapping("/main")
    public ResponseEntity<Result<PortFolioDto>> modifyMainPortFolio(
            @Parameter(hidden = true) @LoginId Optional<Long> idHolder,
            @Validated @RequestBody ModifyPortfolioRequest form
    ) {
        portfolioService.modifyMainPortfolio(memberService.checkAndGetMemberId(idHolder), form.portfolioId());

        return ResponseEntity
                .ok()
                .body(Result.createSingleResult(ResponseCode.SUCCESS.getCode(), null, null));
    }
}
