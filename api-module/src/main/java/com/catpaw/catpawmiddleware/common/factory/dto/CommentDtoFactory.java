package com.catpaw.catpawmiddleware.common.factory.dto;

import com.catpaw.catpawcore.domain.entity.CommentRecruit;
import com.catpaw.catpawmiddleware.service.dto.comment.CommentSummaryDto;

public class CommentDtoFactory {


    public static CommentSummaryDto toCommentSummary(CommentRecruit commentRecruit) {
        CommentSummaryDto dto = new CommentSummaryDto();
        dto.setId(commentRecruit.getId());
        dto.setMemberId(commentRecruit.getMember().getId());
        dto.setNickname(commentRecruit.getMember().getNickname());
        dto.setContent(commentRecruit.getContent());

        return dto;
    }
}
