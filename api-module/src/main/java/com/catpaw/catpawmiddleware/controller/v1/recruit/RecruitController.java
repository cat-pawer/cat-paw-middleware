package com.catpaw.catpawmiddleware.controller.v1.recruit;

import com.catpaw.catpawcore.common.resolver.annotation.LoginId;
import com.catpaw.catpawmiddleware.controller.v1.request.enums.GroupTypeRequest;
import com.catpaw.catpawmiddleware.controller.v1.request.enums.OnlineTypeRequest;
import com.catpaw.catpawmiddleware.controller.v1.request.enums.RecruitStateRequest;
import com.catpaw.catpawmiddleware.controller.v1.request.enums.RecruitTopicRequest;
import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import com.catpaw.catpawmiddleware.controller.v1.response.recruit.RecruitDetailSchema;
import com.catpaw.catpawmiddleware.controller.v1.response.recruit.RecruitSummarySchema;
import com.catpaw.catpawcore.domain.eumns.ResponseCode;
import com.catpaw.catpawcore.domain.dto.repository.RecruitDetailDto;
import com.catpaw.catpawcore.domain.dto.service.CustomPageDto;
import com.catpaw.catpawcore.domain.dto.service.recruit.RecruitSearchDto;
import com.catpaw.catpawcore.domain.dto.service.recruit.RecruitSummaryDto;
import com.catpaw.catpawcore.domain.dto.service.recruit.RecruitTopicDto;
import com.catpaw.catpawmiddleware.service.recruit.RecruitService;
import com.catpaw.catpawcore.utils.PageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Tag(name = "모집", description = "모집 도메인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recruit")
public class RecruitController {

    private final RecruitService recruitService;

    @Operation(
            summary = "프로젝트 상세 조회",
            description = "본인 게시글은 전부 접근 가능 / 숨김 상태 시 본인 외 접근 불가",
            tags = { "Get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "정상", content = { @Content(schema = @Schema(implementation = RecruitDetailSchema.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "403",  description = "권한 없는 모집글", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",  description = "존재하지 않는 모집글", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")})})
    @GetMapping("/detail/{id}")
    public ResponseEntity<Result<RecruitDetailDto>> recruitDetail(
            @Parameter(description = "조회글 id") @PathVariable("id") Long recruitId,
            @Parameter(hidden = true) @LoginId Optional<Long> idHolder
    ) {
        RecruitDetailDto recruitDetailDto =
                recruitService.getAccessibleRecruitDetail(recruitId, idHolder.orElse(null));

        return ResponseEntity
                .ok()
                .body(Result.createSingleResult(ResponseCode.SUCCESS.getCode(), null, recruitDetailDto));
    }

    @Operation(
            summary = "프로젝트 목록 조회",
            description = "전체 프로젝트 필터 페이지 또는 스크롤 조회",
            tags = { "Get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "정상", content = { @Content(schema = @Schema(implementation = RecruitSummarySchema.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",  description = "잘못된 검색조건", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")})})
    @GetMapping("/summary/search")
    public ResponseEntity<Result<CustomPageDto<RecruitSummaryDto>>> recruitSummarySearch(
            @Parameter(description = "제목 또는 해쉬태그 검색 값") @RequestParam(required = false) String searchValue,
            @Parameter(description = "프로젝트 또는 스터디 조회") @RequestParam(required = false) GroupTypeRequest recruitType,
            @Parameter(description = "진행방식 검색") @RequestParam(required = false) OnlineTypeRequest onlineType,
            @Parameter(description = "프로젝트 상태") @RequestParam(required = false) RecruitStateRequest recruitState,
            @Parameter(description = "검색 기준일 (default = 현재일자)") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate recruitPeriod,
            @Parameter(description = "특정 카테고리 조회") @RequestParam(required = false) List<Long> categoryIdList,
            @Parameter(description = "페이지 또는 스크롤 조회") @RequestParam(required = false, defaultValue = "false") Boolean isPage,
            @Parameter(description = "페이지 값 (정렬기준 created 가능)") @PageableDefault(sort = "created", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (PageUtils.checkInvalidSort(pageable, PageUtils.CREATED)) {
            throw new IllegalArgumentException("지원하지 않는 정렬조건입니다.");
        }

        RecruitSearchDto searchDto = new RecruitSearchDto();
        searchDto.setSearchValue(searchValue);
        if (recruitType != null) searchDto.setRecruitType(recruitType.toEnum());
        if (onlineType != null) searchDto.setOnlineType(onlineType.toEnum());
        if (recruitState != null) searchDto.setState(recruitState.toEnum());
        searchDto.setRecruitPeriod(recruitPeriod);
        searchDto.setCategoryIdList(categoryIdList);

        CustomPageDto<RecruitSummaryDto> result =
                recruitService.getRecruitSummaryForSearch(searchDto, pageable, isPage);

        return ResponseEntity
                .ok()
                .body(Result.createPageResult(ResponseCode.SUCCESS.getCode(), null, result));
    }

    @Operation(
            summary = "프로젝트 토픽 기반 조회",
            description = "신상 또는 마감임박 프로젝트 페이지 또는 스크롤 조회",
            tags = { "Get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "정상", content = { @Content(schema = @Schema(implementation = RecruitSummarySchema.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",  description = "잘못된 검색조건", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")})})
    @GetMapping("/summary/topics")
    public ResponseEntity<Result<CustomPageDto<RecruitSummaryDto>>> recruitSummarySearchByTopic(
            @Parameter(description = "검색 토픽 값") @RequestParam RecruitTopicRequest topic,
            @Parameter(description = "프로젝트 상태") @RequestParam(required = false) RecruitStateRequest state,
            @Parameter(description = "검색 기준일 (default = 현재일자)") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate recruitPeriod,
            @Parameter(description = "페이지 또는 스크롤 조회") @RequestParam(required = false, defaultValue = "false") Boolean isPage,
            @Parameter(description = "페이지 값 (정렬기준 created 가능)") @PageableDefault(sort = "created", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (PageUtils.checkInvalidSort(pageable, PageUtils.CREATED)) {
            throw new IllegalArgumentException("지원하지 않는 정렬조건입니다.");
        }

        RecruitTopicDto topicDto = new RecruitTopicDto();
        if (state != null) topicDto.setState(state.toEnum());
        topicDto.setRecruitPeriod(recruitPeriod);
        topicDto.setTopic(topic.toEnum());

        CustomPageDto<RecruitSummaryDto> result =
                recruitService.getRecruitSummaryForTopic(topicDto, pageable, isPage);

        return ResponseEntity
                .ok()
                .body(Result.createPageResult(ResponseCode.SUCCESS.getCode(), null, result));
    }

    @PostMapping("/save")
    public ResponseEntity<Result<Void>> recruitSave() {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Result<Void>> recruitModify(@PathVariable("id") Long recruitId) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> recruitRemove(@PathVariable("id") Long recruitId) {
        return ResponseEntity.ok().build();
    }
}
