package com.catpaw.catpawmiddleware.service.comment;

import com.catpaw.catpawcore.domain.entity.CommentRecruit;
import com.catpaw.catpawcore.domain.entity.Member;
import com.catpaw.catpawcore.domain.entity.Recruit;
import com.catpaw.catpawcore.exception.custom.ForbiddenException;
import com.catpaw.catpawmiddleware.repository.comment.CommentRecruitRepository;
import com.catpaw.catpawcore.domain.dto.repository.RecruitDetailDto;
import com.catpaw.catpawmiddleware.service.MockBaseTest;
import com.catpaw.catpawcore.domain.dto.service.comment.CommentDetailDto;
import com.catpaw.catpawcore.service.member.MemberService;
import com.catpaw.catpawmiddleware.service.recruit.RecruitService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
class CommentRecruitServiceTest extends MockBaseTest {

    @InjectMocks
    CommentRecruitService commentRecruitService;

    @Mock
    CommentRecruitRepository commentRepository;

    @Mock
    MemberService memberService;

    @Mock
    RecruitService recruitService;

    @Test
    @DisplayName("getCommentSummary page 미지원")
    void getCommentSummaryPage() {
        //given
        Long recruitId = 1L;
        PageRequest pageable = PageRequest.of(0, 20, Sort.by(new Sort.Order(Sort.Direction.DESC, "created")));

        //when
        //then
        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> commentRecruitService.getCommentSummary(recruitId, pageable, true));
    }

    @Test
    @DisplayName("getCommentSummary 정상 케이스")
    void getCommentSummarySlice() {
        //given
        Long recruitId = 1L;
        PageRequest pageable = PageRequest.of(0, 20, Sort.by(new Sort.Order(Sort.Direction.DESC, "created")));
        given(commentRepository.findSlicedCommentListByRecruitId(anyLong(), any(Pageable.class)))
                .willReturn(new SliceImpl<>(List.of(), pageable, true));

        //when
        commentRecruitService.getCommentSummary(recruitId, pageable, false);

        //then
        verify(commentRepository, times(1))
                .findSlicedCommentListByRecruitId(anyLong(), any(Pageable.class));

    }

    @Test
    @DisplayName("addComment 정상 케이스")
    void addComment() {
        //given
        Long recruitId = 1L;
        Long memberId = 1L;
        CommentDetailDto commentDetailDto = new CommentDetailDto();
        commentDetailDto.setRecruitId(recruitId);
        commentDetailDto.setMemberId(memberId);
        commentDetailDto.setContent("hello");
        RecruitDetailDto recruitDetailDto = new RecruitDetailDto();
        recruitDetailDto.setId(recruitId);
        given(recruitService.getAccessibleRecruitDetail(anyLong(), anyLong()))
                .willReturn(recruitDetailDto);
        given(memberService.getReferenceMember(anyLong()))
                .willReturn(new Member());
        given(recruitService.getReferenceRecruit(anyLong()))
                .willReturn(new Recruit());

        //when
        commentRecruitService.addComment(commentDetailDto);

        //then
        verify(recruitService, times(1))
                .getAccessibleRecruitDetail(anyLong(), anyLong());
        verify(memberService, times(1))
                .getReferenceMember(anyLong());
        verify(recruitService, times(1))
                .getReferenceRecruit(anyLong());
    }

    @Test
    @DisplayName("removeComment 권한 없는 사용자")
    void removeCommentPermissionDenied() {
        //given
        Long commentId = 1L;
        Long memberId = 1L;
        CommentRecruit commentRecruit = new CommentRecruit();
        given(commentRepository.findById(anyLong())).willReturn(Optional.of(commentRecruit));

        //when
        //then
        Assertions.assertThrows(ForbiddenException.class,
                () -> commentRecruitService.removeComment(commentId, memberId));
    }

    @Test
    @DisplayName("removeComment 정상 케이스")
    void removeComment() {
        //given
        Long commentId = 1L;
        Long memberId = 1L;
        CommentRecruit commentRecruit = new CommentRecruit();
        commentRecruit.changeCreatedBy(memberId);
        given(commentRepository.findById(anyLong())).willReturn(Optional.of(commentRecruit));

        //when
        commentRecruitService.removeComment(commentId, memberId);
        //then
        verify(commentRepository, times(1)).findById(anyLong());
    }
}