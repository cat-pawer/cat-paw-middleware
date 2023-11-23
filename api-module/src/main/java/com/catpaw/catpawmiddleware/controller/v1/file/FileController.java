package com.catpaw.catpawmiddleware.controller.v1.file;

import com.catpaw.catpawmiddleware.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

//    @PostMapping
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            String fileUrl = fileService.upload(file, new FileTarget(1, TargetType.RECRUIT));
////            String fileUrl = "ok";
//
//            return ResponseEntity.ok(fileUrl);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @PostMapping("/remove/{id}")
//    public ResponseEntity<Void> removeFile(@PathVariable long id) {
//        try {
//            fileService.remove(id);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}
