package com.catpaw.catpawcore.domain.object.group;

import lombok.Getter;

@Getter
public class GroupBoardContents {

    private Long boardId;

    private String title;

    private String content;

    public GroupBoardContents(Long boardId, String title, String content) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }
}
