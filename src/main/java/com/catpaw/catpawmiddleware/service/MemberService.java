package com.catpaw.catpawmiddleware.service;

import com.catpaw.catpawmiddleware.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final BoardRepository boardRepository;

    @Transactional
    public String example() {
        boardRepository.findCommentListByGroupBoard(2L);

        boardRepository.findById(1L);

        return "hello";
    }
}
