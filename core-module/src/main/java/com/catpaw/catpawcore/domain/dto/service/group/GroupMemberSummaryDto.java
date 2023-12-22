package com.catpaw.catpawcore.domain.dto.service.group;

import com.catpaw.catpawcore.domain.eumns.Auth;
import com.catpaw.catpawcore.domain.eumns.GroupMemberState;

public class GroupMemberSummaryDto {

    private Long id;

    private Long memberId;

    private String name;

    private Auth auth;

    private GroupMemberState state;

    public void setId(Long id) {
        this.id = id;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public void setState(GroupMemberState state) {
        this.state = state;
    }
}
