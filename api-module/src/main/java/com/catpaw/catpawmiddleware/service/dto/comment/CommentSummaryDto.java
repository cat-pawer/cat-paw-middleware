package com.catpaw.catpawmiddleware.service.dto.comment;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class CommentSummaryDto {

    private Long id;

    private Long memberId;

    private String nickname;

    private String content;
}
