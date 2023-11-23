package com.catpaw.catpawmiddleware.controller.v1.comment;

import com.catpaw.catpawmiddleware.common.resolver.annotation.LoginId;
import com.catpaw.catpawmiddleware.controller.v1.request.comment.AddCommentForm;
import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import com.catpaw.catpawmiddleware.controller.v1.response.comment.CommentSummarySchema;
import com.catpaw.catpawcore.domain.eumns.ResponseCode;
import com.catpaw.catpawmiddleware.service.comment.CommentRecruitService;
import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import com.catpaw.catpawmiddleware.service.dto.comment.CommentDetailDto;
import com.catpaw.catpawmiddleware.service.dto.comment.CommentSummaryDto;
import com.catpaw.catpawmiddleware.service.member.MemberService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "댓글", description = "댓글 도메인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentRecruitService commentRecruitService;
    private final MemberService memberService;

    @Operation(
            summary = "모집글 댓글 조회",
            tags = { "Get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "정상", content = { @Content(schema = @Schema(implementation = CommentSummarySchema.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",  description = "잘못된 검색조건", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")})})
    @GetMapping("/summary/{recruitId}")
    public ResponseEntity<Result<CustomPageDto<CommentSummaryDto>>> commentSummary(
            @PathVariable Long recruitId,
            @Parameter(description = "페이지 또는 스크롤 조회") @RequestParam(required = false, defaultValue = "false") Boolean isPage,
            @Parameter(description = "페이지 값 (정렬기준 created 가능)") @PageableDefault(sort = "created", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (PageUtils.checkInvalidSort(pageable, PageUtils.CREATED)) {
            throw new IllegalArgumentException("잘못된 정렬조건 입니다.");
        }

        CustomPageDto<CommentSummaryDto> commentSummary =
                commentRecruitService.getCommentSummary(recruitId, pageable, isPage);

        return ResponseEntity
                .ok()
                .body(Result.createPageResult(ResponseCode.SUCCESS.getCode(), null, commentSummary));
    }

    @Operation(
            summary = "모집글 댓글 추가",
            tags = { "Post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "정상", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",  description = "잘못된 입력", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401",  description = "인증되지 않은 사용자", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "403",  description = "접근 권한 없는 모집글", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",  description = "존재하지 않는 모집글", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")})})
    @PostMapping("/save")
    public ResponseEntity<Result<Void>> commentSave(
            @Validated @RequestBody AddCommentForm addCommentForm,
            @Parameter(hidden = true) @LoginId Optional<Long> idHolder
    ) {
        long memberId = memberService.checkAndGetMemberId(idHolder);

        CommentDetailDto commentDetailDto = new CommentDetailDto();
        commentDetailDto.setMemberId(memberId);
        commentDetailDto.setRecruitId(addCommentForm.getRecruitId());
        commentDetailDto.setContent(addCommentForm.getContent());

        commentRecruitService.addComment(commentDetailDto);

        return ResponseEntity
                .ok()
                .body(Result.createSingleResult(ResponseCode.SUCCESS.getCode(), null, null));
    }

    @Operation(
            summary = "모집글 댓글 삭제",
            tags = { "Delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "정상", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",  description = "잘못된 입력", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401",  description = "인증되지 않은 사용자", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "403",  description = "삭제 권한 없는 댓글", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",  description = "존재하지 않는 댓글", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")})})
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Result<Void>> commentRemove(
            @PathVariable("commentId") Long commentId,
            @Parameter(hidden = true) @LoginId Optional<Long> idHolder
    ) {
        long memberId = memberService.checkAndGetMemberId(idHolder);
        commentRecruitService.removeComment(commentId, memberId);

        return ResponseEntity
                .ok()
                .body(Result.createSingleResult(ResponseCode.SUCCESS.getCode(), null, null));
    }
}
