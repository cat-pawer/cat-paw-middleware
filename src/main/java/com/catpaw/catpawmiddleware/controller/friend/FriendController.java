package com.catpaw.catpawmiddleware.controller.friend;

import com.catpaw.catpawmiddleware.common.constants.SortConst;
import com.catpaw.catpawmiddleware.common.handler.exception.custom.DuplicateFriendException;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/friends")
    public ResponseEntity<Result<CustomPageDto<FriendSummaryDto>>> getFriendList(
            @LoginId Optional<Long> idHolder,
            @RequestParam(value = "myRequest", required = false) Boolean myRequest,
            @RequestParam(value = "otherRequest", required = false) Boolean otherRequest,
            @RequestParam(value = "isPage", defaultValue = "false") Boolean isPage,
            @RequestParam(value = "state", required = false) FriendState state,
            @RequestParam(value = "name", required = false) String name,
            @PageableDefault(sort = "updated", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (!PageUtils.checkSortValid(pageable, SortConst.UPDATED))
            throw new IllegalArgumentException("정렬 값이 올바르지 않습니다.");

        Long memberId = idHolder.orElseThrow(() -> {
            throw new UsernameNotFoundException("로그인하지 않은 사용자입니다.");
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

    @PostMapping("/add/friend")
    public ResponseEntity<Result<Void>> addFriend(@RequestBody AddFriendForm form, @LoginId Optional<Long> idHolder) {
        Long memberId = idHolder.orElseThrow(() -> {
            throw new UsernameNotFoundException("로그인하지 않은 사용자입니다.");
        });

        try {
            friendService.addFriend(memberId, form.targetId());
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
