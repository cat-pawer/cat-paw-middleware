package com.catpaw.catpawcore.domain.dto.service.group;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class GroupBoardSummaryDto {

    private Long id;

    private Long groupId;

    private String title;

    private String content;

    private Long likeCount;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Long createdBy;

    private Long updatedBy;
}
