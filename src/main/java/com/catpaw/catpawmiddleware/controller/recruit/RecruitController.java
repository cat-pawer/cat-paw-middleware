package com.catpaw.catpawmiddleware.controller.recruit;

import com.catpaw.catpawmiddleware.controller.request.search.SearchForm;
import com.catpaw.catpawmiddleware.controller.response.Result;
import com.catpaw.catpawmiddleware.domain.eumns.GroupType;
import com.catpaw.catpawmiddleware.domain.eumns.OnlineType;
import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import com.catpaw.catpawmiddleware.domain.eumns.ResponseCode;
import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitSearchDto;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitSummaryDto;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitTopicDto;
import com.catpaw.catpawmiddleware.service.recruit.RecruitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "모집", description = "모집 도메인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruit")
public class RecruitController {

    private final RecruitService recruitService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<Result<Void>> recruitDetail(@PathVariable("id") Long recruitId) {
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/summary")
//    public ResponseEntity<Result<CustomPageDto<RecruitSummaryDto>>> recruitSummary(
//            @PageableDefault(sort = "created", direction = Sort.Direction.DESC) Pageable pageable
//    ) {
//        CustomPageDto<RecruitSummaryDto> result;
//        if (pageable.isPaged()) result = recruitService.getPagedRecruitSummary(pageable);
//        else result = recruitService.getSlicedRecruitSummary(pageable);
//
//        return ResponseEntity
//                .ok()
//                .body(Result.createPageResult(ResponseCode.SUCCESS.getCode(), null, result));
//    }

    @GetMapping("/summary/search")
    public ResponseEntity<Result<CustomPageDto<RecruitSummaryDto>>> recruitSummarySearch(
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) GroupType recruitType,
            @RequestParam(required = false) OnlineType onlineType,
            @RequestParam(required = false) RecruitState recruitState,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate recruitPeriod,
            @RequestParam(required = false) List<Long> categoryIdList,
            @PageableDefault(sort = "created", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        RecruitSearchDto searchDto = new RecruitSearchDto();
        searchDto.setSearchValue(searchValue);
        searchDto.setRecruitType(recruitType);
        searchDto.setOnlineType(onlineType);
        searchDto.setState(recruitState);
        searchDto.setRecruitPeriod(recruitPeriod);
        searchDto.setCategoryIdList(categoryIdList);

        CustomPageDto<RecruitSummaryDto> result =
                recruitService.getRecruitSummaryForSearch(searchDto, pageable);

        return ResponseEntity
                .ok()
                .body(Result.createPageResult(ResponseCode.SUCCESS.getCode(), null, result));
    }

    @GetMapping("/summary/topics")
    public ResponseEntity<Result<CustomPageDto<RecruitSummaryDto>>> recruitSummarySearchByTopic(
            @RequestParam String topic,
            @RequestParam(required = false) RecruitState state,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate recruitPeriod,
            Pageable pageable
    ) {
        List<String> supportTopicList = List.of(SearchForm.DEADLINE.getValue(), SearchForm.ISNEW.getValue());
        if (!supportTopicList.contains(topic)) {
            throw new IllegalArgumentException("잘못된 검색 조건입니다.");
        }

        RecruitTopicDto topicDto = new RecruitTopicDto();
        topicDto.setState(state);
        topicDto.setRecruitPeriod(recruitPeriod);
        topicDto.setTopic(topic);

        CustomPageDto<RecruitSummaryDto> result =
                recruitService.getRecruitSummaryForTopic(topicDto, pageable);

        return ResponseEntity
                .ok()
                .body(Result.createPageResult(ResponseCode.SUCCESS.getCode(), null, result));
    }

    @PostMapping("/")
    public ResponseEntity<Result<Void>> recruitSave() {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Result<Void>> recruitModify(@PathVariable("id") Long recruitId) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> recruitRemove(@PathVariable("id") Long recruitId) {
        return ResponseEntity.ok().build();
    }
}
