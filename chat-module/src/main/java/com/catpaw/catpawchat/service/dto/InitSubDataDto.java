package com.catpaw.catpawchat.service.dto;

import com.catpaw.catpawcore.service.dto.CommonCodeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class InitSubDataDto {

    private List<GroupMemberDto> totalMemberList = new ArrayList<>();

    private List<ParticipationDto> currentMemberList = new ArrayList<>();

    private List<CommonCodeDto> codeList = new ArrayList<>();

    public InitSubDataDto(List<GroupMemberDto> totalMemberList, List<ParticipationDto> currentMemberList, List<CommonCodeDto> codeList) {
        this.totalMemberList = totalMemberList;
        this.currentMemberList = currentMemberList;
        this.codeList = codeList;
    }
}
