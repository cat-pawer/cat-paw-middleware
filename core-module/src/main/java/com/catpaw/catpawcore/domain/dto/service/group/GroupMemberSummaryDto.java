package com.catpaw.catpawcore.domain.dto.service.group;

import com.catpaw.catpawcore.domain.eumns.Auth;
import com.catpaw.catpawcore.domain.eumns.GroupMemberState;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupMemberSummaryDto {

    private Long id;

    private Long memberId;

    private String name;

    private Auth auth;

    private GroupMemberState state;
}
