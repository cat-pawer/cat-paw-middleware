package com.catpaw.catpawmiddleware.controller.friend;

import com.catpaw.catpawmiddleware.common.resolver.annotation.LoginId;
import com.catpaw.catpawmiddleware.controller.response.Result;
import com.catpaw.catpawmiddleware.domain.eumns.FriendState;
import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import com.catpaw.catpawmiddleware.service.dto.friend.FriendSearchDto;
import com.catpaw.catpawmiddleware.service.friend.FriendService;
import com.catpaw.catpawmiddleware.service.friend.FriendSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/friends")
    public ResponseEntity<Result<CustomPageDto<FriendSummaryDto>>> getFriendList(
            @LoginId Optional<Long> memberIdHolder,
            @RequestParam(value = "myRequest", required = false) Boolean myRequest,
            @RequestParam(value = "otherRequest", required = false) Boolean otherRequest,
            @RequestParam(value = "isPage", defaultValue = "false") Boolean isPage,
            @RequestParam(value = "state", required = false) FriendState state,
            @RequestParam(value = "name", required = false) String name,
            @PageableDefault(sort = "updated", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Long memberId = memberIdHolder.orElseThrow(() -> {
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
                .body(Result.createPageResult(0, null, pagedFriendSummary));
    }

    @PostMapping("/add/friend")
    public ResponseEntity<Void> addFriend() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/friend")
    public ResponseEntity<Void> updateFriend() {
        return ResponseEntity.ok().build();
    }

}
