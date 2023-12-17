package com.catpaw.catpawcore.domain.dto.service.group;

import com.catpaw.catpawcore.domain.dto.service.member.MemberSummaryDto;
import com.catpaw.catpawcore.domain.dto.service.recruit.RecruitSummaryDto;
import com.catpaw.catpawcore.domain.eumns.GroupState;
import com.catpaw.catpawcore.domain.eumns.GroupType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class GroupsDetailDto {

    private Long id;

    private String name;

    private String detail;

    private GroupState state;

    private GroupType type;

    private MemberSummaryDto creator;

    private List<MemberSummaryDto> memberList = new ArrayList<>();

    private List<RecruitSummaryDto> recruitList = new ArrayList<>();
}
