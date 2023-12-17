package com.catpaw.catpawcore.domain.dto.service.group;

import com.catpaw.catpawcore.domain.dto.service.member.MemberSummaryDto;
import com.catpaw.catpawcore.domain.eumns.GroupState;
import com.catpaw.catpawcore.domain.eumns.GroupType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class GroupsSummaryDto {

    private Long id;

    private String name;

    private String detail;

    private GroupState state;

    private GroupType type;

    private LocalDate endDate;
}
