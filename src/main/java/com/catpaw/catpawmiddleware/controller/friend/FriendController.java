package com.catpaw.catpawmiddleware.controller.friend;

import com.catpaw.catpawmiddleware.exception.custom.DuplicateFriendException;
import com.catpaw.catpawmiddleware.exception.custom.UserNotFoundException;
import com.catpaw.catpawmiddleware.common.resolver.annotation.LoginId;
import com.catpaw.catpawmiddleware.controller.request.AddFriendForm;
import com.catpaw.catpawmiddleware.controller.response.Result;
import com.catpaw.catpawmiddleware.domain.eumns.FriendState;
import com.catpaw.catpawmiddleware.domain.eumns.ResponseCode;
import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import com.catpaw.catpawmiddleware.service.dto.friend.FriendSearchDto;
import com.catpaw.catpawmiddleware.service.friend.FriendService;
import com.catpaw.catpawmiddleware.service.friend.FriendSummaryDto;
import com.catpaw.catpawmiddleware.utils.PageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "친구", description = "친구 도메인 API")
@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @Operation(
            summary = "친구 목록 조회",
            description = "조회하는 사용자의 친구 목록을 페이징 또는 슬라이스 조회",
            tags = { "Get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "정상", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")})})
    @GetMapping("/friends")
    public ResponseEntity<Result<CustomPageDto<FriendSummaryDto>>> getFriendList(
            @Schema(hidden = true) @LoginId Optional<Long> idHolder,
            @RequestParam(value = "myRequest", required = false) Boolean myRequest,
            @RequestParam(value = "otherRequest", required = false) Boolean otherRequest,
            @RequestParam(value = "isPage", defaultValue = "false") Boolean isPage,
            @RequestParam(value = "state", required = false) FriendState state,
            @RequestParam(value = "name", required = false) String name,
            @PageableDefault(sort = "updated", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (!PageUtils.checkSortValid(pageable, PageUtils.UPDATED))
            throw new IllegalArgumentException("정렬 값이 올바르지 않습니다.");

        Long memberId = idHolder.orElseThrow(() -> {
            throw new UserNotFoundException("로그인하지 않은 사용자입니다.");
        });

        FriendSearchDto friendSearchDto = new FriendSearchDto();
        friendSearchDto.setMemberId(memberId);
        friendSearchDto.setName(name);
        friendSearchDto.setState(state);
        friendSearchDto.setMyRequest(myRequest);
        friendSearchDto.setOtherRequest(otherRequest);

        CustomPageDto<FriendSummaryDto> pagedFriendSummary = isPage ?
                friendService.getPagedFriendSummary(friendSearchDto, pageable) :
                friendService.getSlicedFriendList(friendSearchDto, pageable);

        return ResponseEntity
                .ok()
                .body(Result.createPageResult(ResponseCode.SUCCESS.getCode(), null, pagedFriendSummary));
    }

    @Operation(
            summary = "친구 요청",
            tags = { "Post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",  description = "정상", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = Result.class), mediaType = "application/json")})})
    @PostMapping("/add/friend")
    public ResponseEntity<Result<Void>> addFriend(
            @RequestBody AddFriendForm form,
            @Schema(hidden = true) @LoginId Optional<Long> idHolder) {
        Long memberId = idHolder.orElseThrow(() -> {
            throw new UsernameNotFoundException("로그인하지 않은 사용자입니다.");
        });

        try {
            friendService.addFriend(memberId, form.getTargetId());
            return ResponseEntity
                    .ok()
                    .body(Result.createSingleResult(ResponseCode.SUCCESS.getCode(), null, null));
        }
        catch (DuplicateFriendException e) {
            return ResponseEntity
                    .ok()
                    .body(Result.createSingleResult(ResponseCode.DUPLICATE_FRIEND.getCode(), null, null));
        }
    }

    @PostMapping("/update/friend")
    public ResponseEntity<Void> updateFriend() {
        return ResponseEntity.ok().build();
    }

}
