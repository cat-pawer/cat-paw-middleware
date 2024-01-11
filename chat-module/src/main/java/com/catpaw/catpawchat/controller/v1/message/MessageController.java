package com.catpaw.catpawchat.controller.v1.message;

import com.catpaw.catpawchat.controller.v1.request.MessageDto;
import com.catpaw.catpawchat.service.MessageService;
import com.catpaw.catpawchat.service.RedisService;
import com.catpaw.catpawchat.service.dto.*;
import com.catpaw.catpawcore.utils.LogUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final RedisService redisService;


    @MessageMapping("/message/init")
    public void doInit(MessageDto<Void, Void> requestDto) {
        Assert.notNull(requestDto, LogUtils.notNullFormat("requestDto"));

        MessageDto<InitDataDto, InitSubDataDto> responseDto =
                messageService.doInit(requestDto.getMemberId(), requestDto.getGroupId());

        redisService.broadcast("/result", responseDto);
    }

    @MessageMapping("/message/init_complete")
    public void doInitComplete(MessageDto<Void, Void> requestDto) {
        Assert.notNull(requestDto, LogUtils.notNullFormat("requestDto"));

        MessageDto<Void, Void> responseDto = messageService.doInitComplete(requestDto.getMemberId(), requestDto.getGroupId());

        redisService.broadcast("/result", responseDto);
    }

    @MessageMapping("/message/init_data")
    public void doInitData(MessageDto<List<ScheduleDto>, Void> requestDto) {
        Assert.notNull(requestDto, LogUtils.notNullFormat("requestDto"));

        redisService.broadcast("/result", requestDto);
    }

    @MessageMapping("/message/update")
    public void doUpdate(MessageDto<String, String> messageDto) {
        MessageDto<?, ?> responseDto = messageService.update(messageDto);

        redisService.broadcast("/result", responseDto);
    }

//    @MessageMapping("/message/leave")
//    public void doLeave(MessageDto<Void, Void> messageDto) {
//        MessageDto<Void, Void> responseDto = messageService.leave(messageDto.getMemberId(), messageDto.getGroupId());
//
//        redisTemplate.convertAndSend("/result", responseDto);
//    }
}
