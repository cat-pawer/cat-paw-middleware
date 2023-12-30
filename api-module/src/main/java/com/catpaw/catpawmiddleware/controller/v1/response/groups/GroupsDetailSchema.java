package com.catpaw.catpawmiddleware.controller.v1.response.groups;

import com.catpaw.catpawcore.domain.dto.service.group.GroupsDetailDto;
import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "그룹 상세정보 조회")
public class GroupsDetailSchema extends Result<GroupsDetailDto> {

    @Schema(description = "응답 코드")
    private int code;

    @Schema(description = "응답 메세지")
    private String message;

    @Schema(description = "응답 데이터")
    private GroupsDetailDto data;
}
