package com.catpaw.catpawmiddleware.repository.dto;

import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class CommentDetailDto {

    private Long id;

    private Long targetId;

    private TargetType type;

    private String content;
}
