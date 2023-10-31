package com.catpaw.catpawmiddleware.controller.recruit;

import com.catpaw.catpawmiddleware.controller.response.Result;
import com.catpaw.catpawmiddleware.service.recruit.RecruitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Result<Void>> recruitSummary() {
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
