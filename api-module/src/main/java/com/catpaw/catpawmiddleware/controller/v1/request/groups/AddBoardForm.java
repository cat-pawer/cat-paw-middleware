package com.catpaw.catpawmiddleware.controller.v1.request.groups;

import com.catpaw.catpawcore.domain.object.group.GroupBoardContents;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "그룹 게시물 폼")
public class AddBoardForm {

    @Schema(description = "그룹 id")
    private Long groupId;

    @Schema(description = "게시물 제목")
    private String title;

    @Schema(description = "게시물 내용")
    private String content;

    public GroupBoardContents toGroupBoardContents() {
        return new GroupBoardContents(null, this.title, this.content);
    }
}
