package com.catpaw.catpawchat.controller.v1.message;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/init")
    public void init() {

    }

}
