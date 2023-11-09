package com.catpaw.catpawmiddleware.repository.comment;

import com.catpaw.catpawmiddleware.domain.entity.CommentRecruit;
import com.catpaw.catpawmiddleware.repository.comment.query.CommentRecruitQueryRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRecruitRepository extends CrudRepository<CommentRecruit, Long>,
        CommentRecruitRepositoryCustom, CommentRecruitQueryRepository {
}
