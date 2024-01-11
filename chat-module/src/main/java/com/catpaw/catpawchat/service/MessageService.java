package com.catpaw.catpawchat.service;

import com.catpaw.catpawchat.controller.v1.request.MessageDto;
import com.catpaw.catpawchat.controller.v1.request.MessageType;
import com.catpaw.catpawchat.service.dto.*;
import com.catpaw.catpawchat.service.group.GroupService;
import com.catpaw.catpawcore.domain.entity.Schedule;
import com.catpaw.catpawcore.domain.entity.ScheduleSummary;
import com.catpaw.catpawcore.domain.eumns.CodeType;
import com.catpaw.catpawcore.exception.custom.DataNotFoundException;
import com.catpaw.catpawcore.exception.custom.ForbiddenException;
import com.catpaw.catpawcore.service.common_code.CommonCodeService;
import com.catpaw.catpawcore.service.dto.CommonCodeDto;
import com.catpaw.catpawcore.utils.LogUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MessageService {

    private final RedisService redisService;
    private final GroupService groupService;
    private final CommonCodeService commonCodeService;
    private final ScheduleService scheduleService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MessageDto<InitDataDto, InitSubDataDto> doInit(Long memberId, Long groupId) {
        Assert.notNull(memberId, LogUtils.notNullFormat("memberId"));
        Assert.notNull(groupId, LogUtils.notNullFormat("groupId"));

        List<GroupMemberDto> totalMemberList = groupService.getGroupMemberDtoList(groupId);
        if (totalMemberList.stream().noneMatch(groupMember -> memberId.equals(groupMember.getMemberId()))) {
            throw new ForbiddenException();
        }

        MessageDto<InitDataDto, InitSubDataDto> messageDto = new MessageDto<>();
        messageDto.setMemberId(memberId);
        messageDto.setGroupId(groupId);
        messageDto.setMessageType(MessageType.INIT);

        ParticipationDto participationDto = new ParticipationDto(memberId, LocalDateTime.now());
        redisService.putIfAbsentHashData("group:" + groupId, "member:" + memberId, participationDto);
        Map<String, ParticipationDto> hashEntries = redisService.getHashEntries("group:" + groupId, ParticipationDto.class);
        List<ParticipationDto> currentMemberList = hashEntries.values().stream().toList();

        List<CommonCodeDto> codeDtoList = commonCodeService.getCodeListByType(CodeType.SCHEDULE);

        Optional<ParticipationDto> findParticipation = currentMemberList.stream()
                .filter(ParticipationDto::isReady)
                .filter(participation -> !participation.getMemberId().equals(memberId))
                .findAny();

        InitDataDto initDataDto = new InitDataDto();
        initDataDto.setReady(findParticipation.isEmpty());
        if (findParticipation.isEmpty()) initDataDto.setScheduleDtoList(groupService.getScheduleSummaryList(groupId));
        else messageDto.setTargetId(findParticipation.get().getMemberId());

        messageDto.setData(initDataDto);
        messageDto.setSubData(new InitSubDataDto(totalMemberList, currentMemberList, codeDtoList));

        return messageDto;
    }

    public MessageDto<Void, Void> doInitComplete(Long memberId, Long groupId) {
        ParticipationDto participationDto = redisService.getHashData("group:" + groupId, "member:" + memberId, ParticipationDto.class)
                .orElseThrow(() -> { throw new DataNotFoundException(); });
        participationDto.setReady(true);

        redisService.putHashData("group:" + groupId, "member:" + memberId, participationDto);

        MessageDto<Void, Void> messageDto = new MessageDto<>();
        messageDto.setMemberId(memberId);
        messageDto.setGroupId(groupId);
        messageDto.setMessageType(MessageType.INIT_COMPLETE);

        return messageDto;
    }


    public MessageDto<?, ?> update(MessageDto<String, String> messageDto) {
        try {
            switch (messageDto.getMessageType()) {
                case ADD_SUMMARY -> {
                    ScheduleSummary scheduleSummary = scheduleService.addSummary(messageDto.getTargetId(), messageDto.getData());
                    ScheduleSummaryDto scheduleSummaryDto = new ScheduleSummaryDto(scheduleSummary);

                    MessageDto<ScheduleSummaryDto, Void> result = new MessageDto<>();
                    result.setMemberId(messageDto.getMemberId());
                    result.setGroupId(messageDto.getGroupId());
                    result.setMessageType(messageDto.getMessageType());
                    result.setTargetId(messageDto.getTargetId());
                    result.setData(scheduleSummaryDto);
                    return result;
                }
                case UPDATE_SUMMARY -> {
                    ScheduleSummaryDto scheduleSummaryDto = objectMapper.readValue(messageDto.getData(), ScheduleSummaryDto.class);
                    ScheduleSummaryDto updatedSummary = scheduleService.updateSummary(scheduleSummaryDto);

                    MessageDto<ScheduleSummaryDto, Void> result = new MessageDto<>();
                    result.setMemberId(messageDto.getMemberId());
                    result.setGroupId(messageDto.getGroupId());
                    result.setTargetId(messageDto.getTargetId());
                    result.setMessageType(messageDto.getMessageType());
                    result.setData(updatedSummary);
                    return result;
                }
                case REMOVE_SUMMARY -> {
                    List<Integer> idList =
                            (List<Integer>) objectMapper.readValue(messageDto.getData(), List.class);
                    List<Long> summaryIdList = idList.stream().mapToLong(Integer::longValue).boxed().toList();
                    scheduleService.removeSummaryList(summaryIdList);

                    MessageDto<List<Long>, Void> result = new MessageDto<>();
                    result.setMemberId(messageDto.getMemberId());
                    result.setGroupId(messageDto.getGroupId());
                    result.setMessageType(messageDto.getMessageType());
                    result.setTargetId(messageDto.getTargetId());
                    result.setData(summaryIdList);
                    return result;
                }
                case ADD_SCHEDULE -> {
                    Schedule schedule = scheduleService.addSchedule(messageDto.getGroupId(), messageDto.getData(), null);
                    ScheduleDto scheduleDto = new ScheduleDto(schedule);

                    MessageDto<ScheduleDto, Void> result = new MessageDto<>();
                    result.setMemberId(messageDto.getMemberId());
                    result.setGroupId(messageDto.getGroupId());
                    result.setMessageType(messageDto.getMessageType());
                    result.setData(scheduleDto);
                    return result;
                }
                case REMOVE_SCHEDULE -> {
                    scheduleService.removeSchedule(messageDto.getTargetId());

                    MessageDto<Void, Void> result = new MessageDto<>();
                    result.setMemberId(messageDto.getMemberId());
                    result.setGroupId(messageDto.getGroupId());
                    result.setTargetId(messageDto.getTargetId());
                    result.setMessageType(messageDto.getMessageType());
                    return result;
                }
                default -> {
                    return null;
                }
            }

        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public MessageDto<Void, Void> leave(Long memberId, Long groupId) {
        Assert.notNull(memberId, LogUtils.notNullFormat("memberId"));
        Assert.notNull(groupId, LogUtils.notNullFormat("groupId"));

        redisService.deleteHashData("group:" + groupId, "member:" + memberId);

        MessageDto<Void, Void> result = new MessageDto<>();
        result.setMemberId(memberId);
        result.setGroupId(groupId);
        result.setMessageType(MessageType.LEAVE);

        return result;
    }
}
