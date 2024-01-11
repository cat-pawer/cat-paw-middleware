package com.catpaw.catpawchat.repository.group;

import com.catpaw.catpawchat.service.dto.ScheduleDto;
import com.catpaw.catpawcore.domain.entity.Groups;
import com.catpaw.catpawcore.domain.entity.ScheduleSummary;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepositoryCustom {


    List<Groups> findGroupListByMemberId(Long memberId);

    List<ScheduleDto> findScheduleSummaryByGroupId(@Param("groupId") long groupId);
}
