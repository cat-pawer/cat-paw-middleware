package com.catpaw.catpawcore.domain.dto.service.comment;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class CommentSummaryDto {

    private Long id;

    private Long memberId;

    private String nickname;

    private String content;
}
