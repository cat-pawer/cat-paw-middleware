package com.catpaw.catpawmiddleware.service.dto.comment;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class CommentDetailDto {

    private Long id;

    private Long memberId;

    private Long recruitId;

    private String content;
}
