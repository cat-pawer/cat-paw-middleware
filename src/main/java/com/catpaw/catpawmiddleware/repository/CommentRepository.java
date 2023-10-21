package com.catpaw.catpawmiddleware.repository;

import com.catpaw.catpawmiddleware.domain.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
