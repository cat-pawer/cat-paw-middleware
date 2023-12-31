package com.catpaw.catpawmiddleware.service.comment;

import com.catpaw.catpawcore.common.factory.dto.CommentDtoFactory;
import com.catpaw.catpawcore.domain.entity.CommentRecruit;
import com.catpaw.catpawcore.domain.entity.Member;
import com.catpaw.catpawcore.domain.entity.Recruit;
import com.catpaw.catpawcore.exception.custom.DataNotFoundException;
import com.catpaw.catpawcore.exception.custom.ForbiddenException;
import com.catpaw.catpawcore.common.factory.dto.CustomPageDtoFactory;
import com.catpaw.catpawmiddleware.repository.comment.CommentRecruitRepository;
import com.catpaw.catpawcore.domain.dto.repository.RecruitDetailDto;
import com.catpaw.catpawcore.domain.dto.service.CustomPageDto;
import com.catpaw.catpawcore.domain.dto.service.comment.CommentDetailDto;
import com.catpaw.catpawcore.domain.dto.service.comment.CommentSummaryDto;
import com.catpaw.catpawmiddleware.service.member.MemberService;
import com.catpaw.catpawmiddleware.service.recruit.RecruitService;
import com.catpaw.catpawcore.utils.LogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentRecruitService {

    private final CommentRecruitRepository commentRepository;
    private final MemberService memberService;
    private final RecruitService recruitService;


    public CustomPageDto<CommentSummaryDto> getCommentSummary(Long recruitId, Pageable pageable, boolean isPage) {
        Assert.notNull(recruitId, LogUtils.notNullFormat("recruitId"));
        Assert.notNull(pageable, LogUtils.notNullFormat("pageable"));

        if (isPage) throw new UnsupportedOperationException("지원하지 않는 메소드입니다.");

        Slice<CommentRecruit> slicedCommentList =
                commentRepository.findSlicedCommentListByRecruitId(recruitId, pageable);

        return CustomPageDtoFactory.createCustomPageDto(slicedCommentList
                .map(CommentDtoFactory::toCommentSummary));
    }

    @Transactional
    public void addComment(CommentDetailDto commentDetailDto) {
        Assert.notNull(commentDetailDto, LogUtils.notNullFormat("commentDetailDto"));
        Assert.notNull(commentDetailDto.getMemberId(), LogUtils.notNullFormat("memberId"));
        Assert.notNull(commentDetailDto.getRecruitId(), LogUtils.notNullFormat("recruitId"));
        Assert.hasText(commentDetailDto.getContent(), LogUtils.notEmptyFormat("content"));

        RecruitDetailDto recruitDetailDto =
                recruitService.getAccessibleRecruitDetail(
                        commentDetailDto.getRecruitId(), commentDetailDto.getMemberId());

        Recruit referenceRecruit = recruitService.getReferenceRecruit(recruitDetailDto.getId());
        Member referenceMember = memberService.getReferenceMember(commentDetailDto.getMemberId());

        CommentRecruit commentRecruit = new CommentRecruit();
        commentRecruit.changeRecruit(referenceRecruit);
        commentRecruit.changeMember(referenceMember);
        commentRecruit.setContent(commentRecruit.getContent());

        commentRepository.save(commentRecruit);
    }

    @Transactional
    public void removeComment(Long commentId, Long memberId) {
        Assert.notNull(commentId, LogUtils.notNullFormat("commentId"));
        Assert.notNull(memberId, LogUtils.notNullFormat("memberId"));

        CommentRecruit commentRecruit = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 댓글입니다.");
        });

        if (!memberId.equals(commentRecruit.getCreatedBy())) {
            throw new ForbiddenException("삭제 권한이 없습니다");
        }

        commentRepository.delete(commentRecruit);
    }
}
