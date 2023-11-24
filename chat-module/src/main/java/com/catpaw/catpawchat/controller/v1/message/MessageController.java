package com.catpaw.catpawchat.controller.v1.message;

import com.catpaw.catpawchat.controller.v1.request.RequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@MessageMapping("/v1")
public class MessageController {

    @MessageMapping("/init")
    @SendTo("/init")
    public void init(RequestDto requestDto) {
        log.info("requestDto = {}", requestDto.toString());
    }

}
