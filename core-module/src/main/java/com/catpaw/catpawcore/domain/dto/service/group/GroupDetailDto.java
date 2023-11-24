package com.catpaw.catpawcore.domain.dto.service.group;

import com.catpaw.catpawcore.domain.dto.service.member.MemberSummaryDto;
import com.catpaw.catpawcore.domain.dto.service.recruit.RecruitSummaryDto;
import com.catpaw.catpawcore.domain.eumns.GroupState;
import com.catpaw.catpawcore.domain.eumns.GroupType;
import com.catpaw.catpawcore.domain.eumns.Scope;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class GroupDetailDto {

    private Long id;

    private String name;

    private String detail;

    private GroupState state;

    private GroupType type;

    private Scope scope;

    private MemberSummaryDto creator;

    private List<MemberSummaryDto> memberList = new ArrayList<>();

    private List<RecruitSummaryDto> recruitList = new ArrayList<>();
}
