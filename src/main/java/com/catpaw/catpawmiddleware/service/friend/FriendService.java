package com.catpaw.catpawmiddleware.service.friend;

import com.catpaw.catpawmiddleware.common.factory.dto.FriendDtoFactory;
import com.catpaw.catpawmiddleware.domain.entity.Friend;
import com.catpaw.catpawmiddleware.repository.condition.FriendSearchCond;
import com.catpaw.catpawmiddleware.repository.friend.FriendRepository;
import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import com.catpaw.catpawmiddleware.service.dto.friend.FriendSearchDto;
import com.catpaw.catpawmiddleware.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    public CustomPageDto<FriendSummaryDto> getPagedFriendSummary(FriendSearchDto searchDto, Pageable pageable) {
        Assert.notNull(searchDto.getMemberId(), "대상 회원이 존재하지 않습니다.");

        FriendSearchCond condition =
                new FriendSearchCond(searchDto.getMemberId(), searchDto.getState(), searchDto.getName());

        Page<Friend> paged;

        if (searchDto.getMyRequest()) {
            paged = friendRepository.findPagedMyRequestFriendList(condition, pageable);
        }
        else if (searchDto.getOtherRequest()) {
            paged = friendRepository.findPagedOtherRequestFriendList(condition, pageable);
        }
        else {
            paged = friendRepository.findPagedFriendList(condition, pageable);
        }

        return PageUtils.createCustomPageDto(paged, FriendDtoFactory::toFriendSummary);
    }

    public CustomPageDto<FriendSummaryDto> getSlicedFriendList(FriendSearchDto searchDto, Pageable pageable) {
        FriendSearchCond condition =
                new FriendSearchCond(searchDto.getMemberId(), searchDto.getState(), searchDto.getName());

        Slice<Friend> paged;

        if (searchDto.getMyRequest()) {
            paged = friendRepository.findSlicedMyRequestFriendList(condition, pageable);
        }
        else if (searchDto.getOtherRequest()) {
            paged = friendRepository.findSlicedOtherRequestFriendList(condition, pageable);
        }
        else {
            paged = friendRepository.findSlicedFriendList(condition, pageable);
        }

        return PageUtils.createCustomPageDto(paged, FriendDtoFactory::toFriendSummary);
    }
}
