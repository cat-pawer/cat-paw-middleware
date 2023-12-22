package com.catpaw.catpawcore.domain.dto.service.group;

import com.catpaw.catpawcore.domain.dto.service.member.MemberSummaryDto;
import com.catpaw.catpawcore.domain.dto.service.recruit.RecruitSummaryDto;
import com.catpaw.catpawcore.domain.eumns.GroupState;
import com.catpaw.catpawcore.domain.eumns.GroupType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class GroupsDetailDto {

    private Long id;

    private String name;

    private String detail;

    private GroupState state;

    private GroupType type;

    private LocalDate endDate;

    private MemberSummaryDto creator;

    private List<GroupMemberSummaryDto> memberList = new ArrayList<>();

    private List<RecruitSummaryDto> recruitList = new ArrayList<>();

    private LocalDateTime created;

    private LocalDateTime updated;
}
