package com.catpaw.catpawmiddleware.repository;

import com.catpaw.catpawmiddleware.domain.entity.Comment;
import com.catpaw.catpawmiddleware.domain.entity.GroupBoard;
import com.catpaw.catpawmiddleware.repository.dto.CommentDetailDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("ID 컬럼 JOIN 테스트")
    void idCanJoin() {
        GroupBoard board = new GroupBoard();
        GroupBoard saved = boardRepository.save(board);

        Comment comment = new Comment();
        comment.setTargetId(board.getId());
        comment.setContent("hello");
        commentRepository.save(comment);

        List<CommentDetailDto> commentListByGroupBoard = boardRepository.findCommentListByGroupBoard(saved.getId());

        Assertions.assertThat(commentListByGroupBoard).isNotEmpty();
    }
}
