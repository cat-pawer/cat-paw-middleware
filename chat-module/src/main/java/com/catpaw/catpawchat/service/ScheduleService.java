package com.catpaw.catpawchat.service;

import com.catpaw.catpawchat.repository.schedule.ScheduleRepository;
import com.catpaw.catpawchat.repository.schedule_summary.ScheduleSummaryRepository;
import com.catpaw.catpawchat.service.dto.ScheduleSummaryDto;
import com.catpaw.catpawchat.service.group.GroupService;
import com.catpaw.catpawcore.domain.entity.Groups;
import com.catpaw.catpawcore.domain.entity.Schedule;
import com.catpaw.catpawcore.domain.entity.ScheduleSummary;
import com.catpaw.catpawcore.exception.custom.DataNotFoundException;
import com.catpaw.catpawcore.utils.LogUtils;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleSummaryRepository scheduleSummaryRepository;
    private final GroupService groupService;


    public ScheduleSummary addSummary(Long scheduleId, String title) {
        Assert.notNull(scheduleId, LogUtils.notNullFormat("scheduleId"));
        Assert.hasText(title, LogUtils.notEmptyFormat("title"));

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> {
            throw new DataNotFoundException();
        });

        ScheduleSummary scheduleSummary = new ScheduleSummary();
        scheduleSummary.addSchedule(schedule);
        scheduleSummary.setTitle(title);

        return scheduleSummaryRepository.save(scheduleSummary);
    }

    public Schedule addSchedule(Long groupId, String title, @Nullable String body) {
        Assert.notNull(groupId, LogUtils.notNullFormat("groupId"));
        Assert.hasText(title, LogUtils.notEmptyFormat("title"));

        Groups groups = groupService.findById(groupId).orElseThrow(() -> {
            throw new DataNotFoundException();
        });

        Schedule schedule = new Schedule();
        schedule.setGroups(groups);
        schedule.setTitle(title);
        schedule.setBody(body);

        return scheduleRepository.save(schedule);
    }

    public void removeSchedule(Long scheduleId) {
        Assert.notNull(scheduleId, LogUtils.notNullFormat("scheduleId"));

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> {
            throw new DataNotFoundException();
        });

        scheduleRepository.delete(schedule);
    }

    @Transactional
    public void removeSummaryList(List<Long> summaryIdList) {
        Assert.notNull(summaryIdList, LogUtils.notNullFormat("summaryIdList"));

        scheduleSummaryRepository.deleteByIdIn(summaryIdList);
    }

    @Transactional
    public ScheduleSummaryDto updateSummary(ScheduleSummaryDto summaryDto) {
        Assert.notNull(summaryDto, LogUtils.notNullFormat("summaryDto"));

        ScheduleSummary scheduleSummary = scheduleSummaryRepository.findById(summaryDto.getId()).orElseThrow(() -> {
            throw new DataNotFoundException();
        });

        scheduleSummary.setTitle(summaryDto.getTitle());
        scheduleSummary.setStartDate(summaryDto.getStartDate());
        scheduleSummary.setEndDate(summaryDto.getEndDate());
        scheduleSummary.setStatusCodeId(summaryDto.getStatusCodeId());

        ScheduleSummary updatedSummary = scheduleSummaryRepository.saveAndFlush(scheduleSummary);

        return new ScheduleSummaryDto(updatedSummary);
    }
}
