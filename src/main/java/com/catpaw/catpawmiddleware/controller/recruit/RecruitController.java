package com.catpaw.catpawmiddleware.controller.recruit;

import com.catpaw.catpawmiddleware.controller.request.search.SearchForm;
import com.catpaw.catpawmiddleware.controller.response.Result;
import com.catpaw.catpawmiddleware.domain.eumns.ResponseCode;
import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitSummaryDto;
import com.catpaw.catpawmiddleware.service.recruit.RecruitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/summary")
    public ResponseEntity<Result<CustomPageDto<RecruitSummaryDto>>> recruitSummary(
            @PageableDefault(sort = "updated", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        CustomPageDto<RecruitSummaryDto> result;

        if (pageable.isPaged()) {
             result = recruitService.getPagedRecruitSummary(pageable);
        }
        else {
            result = recruitService.getSlicedRecruitSummary(pageable);
        }
        return ResponseEntity
                .ok()
                .body(Result.createPageResult(ResponseCode.SUCCESS.getCode(), null, result));
    }

    @GetMapping("/summary/search")
    public ResponseEntity<Result<Void>> recruitSummarySearch(
            @RequestParam(value = "filter") String searchForm,
            @PageableDefault(sort = "updated", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (SearchForm.DEADLINE.getValue().equals(searchForm)) {

        }
        else if (SearchForm.ISNEW.getValue().equals(searchForm)) {

        }
        else {
            throw new IllegalArgumentException("잘못된 검색 조건입니다.");
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/summary/search")
    public ResponseEntity<Result<Void>> recruitSummarySearch() {
        return ResponseEntity.ok().build();
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
