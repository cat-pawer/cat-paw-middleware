package com.catpaw.catpawmiddleware.service.friend;

import com.catpaw.catpawmiddleware.domain.entity.Friend;
import com.catpaw.catpawmiddleware.domain.entity.Member;
import com.catpaw.catpawmiddleware.domain.eumns.Auth;
import com.catpaw.catpawmiddleware.domain.eumns.FriendState;
import com.catpaw.catpawmiddleware.repository.friend.FriendRepository;
import com.catpaw.catpawmiddleware.repository.member.MemberRepository;
import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import com.catpaw.catpawmiddleware.service.dto.friend.FriendSearchDto;
import com.catpaw.catpawmiddleware.service.dto.friend.FriendSummaryDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Transactional
class FriendServiceTest {

    @Autowired
    FriendService service;

    @Autowired
    FriendRepository repository;

    @Autowired
    MemberRepository memberRepository;

    private Member testMember;

    @BeforeEach
    void beforeEach() {
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            members.add(createTestMember(i));
        }
        this.testMember = members.get(0);
        for (int i = 0; i < 50; i++) {
            createMyResolveFriend(members, i + 1);
        }
        for (int i = 51; i < 100; i++) {
            createOtherResolveFriend(members, i + 1);
        }
        for (int i = 101; i < 150; i++) {
            createMyPendingFriend(members, i);
        }
    }

    private void createMyPendingFriend(List<Member> members, int i) {
        Friend friend = new Friend();
        friend.changeFromMember(members.get(0));
        friend.changeToMember(members.get(i + 1));
        friend.setState(FriendState.PENDING);
        repository.save(friend);
    }

    private void createOtherResolveFriend(List<Member> members, int i) {
        Friend friend = new Friend();
        friend.changeFromMember(members.get(i + 1));
        friend.changeToMember(members.get(0));
        friend.setState(FriendState.RESOLVE);
        repository.save(friend);
    }

    private void createMyResolveFriend(List<Member> members, int i) {
        Friend friend = new Friend();
        friend.changeFromMember(members.get(0));
        friend.changeToMember(members.get(i));
        friend.setState(FriendState.RESOLVE);
        repository.save(friend);
    }

    private Member createTestMember(int i) {
        Member member = new Member();
        member.setName("hello" + i);
        member.setAuth(Auth.MEMBER);
        return memberRepository.save(member);
    }


    @Test
    @DisplayName("getPagedFriendSummary 정상 케이스")
    void getPagedFriendSummary() {
        FriendSearchDto dto = new FriendSearchDto();
        dto.setMemberId(testMember.getId());
        dto.setState(FriendState.RESOLVE);
        PageRequest pageable = PageRequest.of(0, 20, Sort.by(new Sort.Order(Sort.Direction.DESC, "updated")));
        CustomPageDto<FriendSummaryDto> result = service.getPagedFriendSummary(dto, pageable);

        Assertions.assertThat(result.getContent().size()).isEqualTo(20);
    }

    @Test
    @DisplayName("getSlicedFriendList 정상 케이스")
    void getSlicedFriendList() {
        FriendSearchDto dto = new FriendSearchDto();
        dto.setMemberId(testMember.getId());
        dto.setState(FriendState.RESOLVE);
        PageRequest pageable = PageRequest.of(0, 20, Sort.by(new Sort.Order(Sort.Direction.DESC, "updated")));
        CustomPageDto<FriendSummaryDto> result = service.getPagedFriendSummary(dto, pageable);

        Assertions.assertThat(result.getContent().size()).isEqualTo(20);
        Assertions.assertThat(result.isHasNext()).isTrue();
    }

    @Test
    @DisplayName("addFriend 정상 케이스")
    void addFriend() {
        // given
        String friendName = "friend";
        Member member = new Member();
        member.setName(friendName);
        member.setAuth(Auth.MEMBER);
        Member save = memberRepository.save(member);

        // when
        service.addFriend(testMember.getId(), save.getId());

        // then
        FriendSearchDto dto = new FriendSearchDto();
        dto.setMyRequest(true);
        dto.setMemberId(testMember.getId());
        dto.setName(friendName);
        dto.setState(FriendState.PENDING);
        PageRequest pageable = PageRequest.of(0, 20, Sort.by(new Sort.Order(Sort.Direction.DESC, "updated")));
        CustomPageDto<FriendSummaryDto> pagedFriendSummary = service.getPagedFriendSummary(dto, pageable);
        Assertions.assertThat(pagedFriendSummary.getContent().size()).isEqualTo(1);
        Assertions.assertThat(pagedFriendSummary.getContent().get(0).getName()).isEqualTo(friendName);
    }
}