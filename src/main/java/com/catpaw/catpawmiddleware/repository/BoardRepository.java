package com.catpaw.catpawmiddleware.repository;

import com.catpaw.catpawmiddleware.domain.entity.GroupBoard;
import com.catpaw.catpawmiddleware.repository.dto.CommentDetailDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends CrudRepository<GroupBoard, Long> {

    @Query("select new com.catpaw.catpawmiddleware.repository.dto.CommentDetailDto(comment.id, comment.targetId, comment.type, comment.content) " +
            "from GroupBoard groupBoard " +
            "join Comment comment on comment.targetId = groupBoard.id " +
            "where groupBoard.id = :boardId")
    List<CommentDetailDto> findCommentListByGroupBoard(@Param("boardId") Long boardId);
}
