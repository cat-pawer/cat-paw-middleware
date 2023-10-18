package com.catpaw.catpawmiddleware.controller;

import com.catpaw.catpawmiddleware.domain.entity.Index;
import com.catpaw.catpawmiddleware.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
public class IndexController {

    private final IndexService indexService;

    @GetMapping("/")
    public String index() {
        Index index = indexService.service();

        return index.getName();
    }
}
