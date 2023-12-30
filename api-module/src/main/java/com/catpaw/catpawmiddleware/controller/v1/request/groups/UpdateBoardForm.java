package com.catpaw.catpawmiddleware.controller.v1.request.groups;

import com.catpaw.catpawcore.domain.object.group.GroupBoardContents;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "그룹 게시판 수정")
public class UpdateBoardForm {

    @Schema(description = "친구 요청 대상 id")
    private Long groupId;

    private Long boardId;

    private String title;

    private String content;

    public GroupBoardContents toGroupBoardContents() {
        return new GroupBoardContents(this.boardId, this.title, this.content);
    }
}
