package com.catpaw.catpawmiddleware.controller.v1.file;

import com.catpaw.catpawcore.common.resolver.annotation.LoginId;
import com.catpaw.catpawcore.domain.dto.repository.PortFolioDto;
import com.catpaw.catpawcore.domain.dto.service.file.FileSummaryDto;
import com.catpaw.catpawcore.domain.dto.service.file.FileTarget;
import com.catpaw.catpawcore.domain.dto.service.group.GroupsSummaryDto;
import com.catpaw.catpawcore.domain.eumns.ResponseCode;
import com.catpaw.catpawcore.domain.eumns.TargetType;
import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import com.catpaw.catpawmiddleware.service.file.FileService;
import com.catpaw.catpawmiddleware.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Tag(name = "파일", description = "파일 도메인 API")
@SecurityRequirement(name = "bearer-token")
@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final MemberService memberService;

    @Operation(
            summary = "포트폴리오 추가",
            description = "사용자 포트폴리오 추가",
            tags = { "Post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "정상", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",  description = "잘못된 파일 업로드", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401",  description = "인증되지 않은 사용자", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")})})
    @PostMapping(value = "/portfolio/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result<Void>> portfolioSave(
            @Parameter(hidden = true) @LoginId Optional<Long> idHolder,
            @Parameter(description = "포토폴리오 파일") @RequestParam("portfolio") MultipartFile portfolio
    ) {
        fileService.upload(
                portfolio, new FileTarget(memberService.checkAndGetMemberId(idHolder), TargetType.PORTFOLIO));

        return ResponseEntity
                .ok()
                .body(Result.createSingleResult(ResponseCode.SUCCESS.getCode(), null, null));
    }
}
