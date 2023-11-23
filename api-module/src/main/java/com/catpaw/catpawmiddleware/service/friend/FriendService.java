package com.catpaw.catpawmiddleware.service.friend;

import com.catpaw.catpawmiddleware.common.factory.dto.CustomPageDtoFactory;
import com.catpaw.catpawmiddleware.common.factory.dto.FriendDtoFactory;
import com.catpaw.catpawcore.exception.custom.DuplicateFriendException;
import com.catpaw.catpawcore.domain.entity.Friend;
import com.catpaw.catpawcore.domain.entity.Member;
import com.catpaw.catpawcore.domain.eumns.Auth;
import com.catpaw.catpawmiddleware.repository.condition.FriendSearchCond;
import com.catpaw.catpawmiddleware.repository.friend.FriendRepository;
import com.catpaw.catpawmiddleware.repository.member.MemberRepository;
import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import com.catpaw.catpawmiddleware.service.dto.friend.FriendSearchDto;
import com.catpaw.catpawmiddleware.service.dto.friend.FriendSummaryDto;
import com.catpaw.catpawcore.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    public CustomPageDto<FriendSummaryDto> getPagedFriendSummary(FriendSearchDto searchDto, Pageable pageable) {
        Assert.notNull(searchDto.getMemberId(), "대상 회원이 존재하지 않습니다.");

        FriendSearchCond condition =
                new FriendSearchCond(searchDto.getMemberId(), searchDto.getState(), searchDto.getName());
        Page<Friend> paged;
        if (searchDto.getMyRequest() != null && searchDto.getMyRequest()) {
            paged = friendRepository.findPagedMyRequestFriendList(condition, pageable);
        }
        else if (searchDto.getOtherRequest() != null && searchDto.getOtherRequest()) {
            paged = friendRepository.findPagedOtherRequestFriendList(condition, pageable);
        }
        else {
            paged = friendRepository.findPagedFriendList(condition, pageable);
        }

        return CustomPageDtoFactory.createCustomPageDto(paged.map(FriendDtoFactory::toFriendSummary));
    }

    public CustomPageDto<FriendSummaryDto> getSlicedFriendSummary(FriendSearchDto searchDto, Pageable pageable) {
        Assert.notNull(searchDto.getMemberId(), "대상 회원이 존재하지 않습니다.");

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

        return CustomPageDtoFactory.createCustomPageDto(paged.map(FriendDtoFactory::toFriendSummary));
    }

    public void addFriend(Long memberId, Long targetId) throws DuplicateFriendException {
        Assert.notNull(memberId, "회원 번호는 필수입니다.");
        Assert.notNull(targetId, "대상 번호는 필수입니다.");

        List<Member> findMembers =
                memberRepository.findByAuthAndIdIn(Auth.MEMBER, List.of(memberId, targetId));
        Optional<Member> fromMember = findMembers.stream()
                .filter(member -> memberId.equals(member.getId()))
                .findAny();
        Optional<Member> toMember = findMembers.stream()
                .filter(member -> targetId.equals(member.getId()))
                .findAny();

        if (fromMember.isEmpty()) {
            throw new IllegalArgumentException("인증되지 않은 요청입니다.");
        }
        if (toMember.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        friendRepository.findDuplicateFriendRequest(fromMember.get().getId(), toMember.get().getId())
                .ifPresent(friend -> { throw new DuplicateFriendException("이미 친구이거나 신청중입니다."); });

        Friend friend = Friend.makeFriendRequest(fromMember.get(), toMember.get());
        friendRepository.save(friend);
    }
}
