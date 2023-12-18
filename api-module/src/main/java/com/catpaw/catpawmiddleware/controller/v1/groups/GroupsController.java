package com.catpaw.catpawmiddleware.controller.v1.groups;

import com.catpaw.catpawcore.common.resolver.annotation.LoginId;
import com.catpaw.catpawcore.domain.dto.service.CustomPageDto;
import com.catpaw.catpawcore.domain.dto.service.group.GroupsSummaryDto;
import com.catpaw.catpawcore.domain.eumns.ResponseCode;
import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import com.catpaw.catpawmiddleware.controller.v1.response.groups.GroupsSummarySchema;
import com.catpaw.catpawmiddleware.service.groups.GroupsService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "그룹", description = "그룹 도메인 API")
@SecurityRequirement(name = "bearer-token")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupsController {

    private final GroupsService groupsService;
    private final MemberService memberService;

    @Operation(
            summary = "참여한 프로젝트 조회",
            description = "프로젝트 및 스터디 스크롤 조회",
            tags = { "Get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "정상", content = { @Content(schema = @Schema(implementation = GroupsSummarySchema.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401",  description = "인증되지 않은 사용자", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")})})
    @GetMapping("/summary")
    public ResponseEntity<Result<CustomPageDto<GroupsSummaryDto>>> myGroupSummary(
            @Parameter(hidden = true) @LoginId Optional<Long> idHolder,
            @Parameter(description = "참여한 프로젝트 혹은 내 프로젝트") @RequestParam(required = false, defaultValue = "true") boolean mine,
            @Parameter(description = "페이지 값") @PageableDefault(sort = "created", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        long memberId = memberService.checkAndGetMemberId(idHolder);
        CustomPageDto<GroupsSummaryDto> result = mine
                ? groupsService.getMyGroupsSummary(memberId, pageable)
                : groupsService.getOtherGroupsSummary(memberId, pageable);

        return ResponseEntity
                .ok()
                .body(Result.createPageResult(ResponseCode.SUCCESS.getCode(), null, result));
    }
}
