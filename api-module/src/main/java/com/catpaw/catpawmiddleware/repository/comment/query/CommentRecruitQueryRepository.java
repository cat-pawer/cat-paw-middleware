package com.catpaw.catpawmiddleware.repository.comment.query;

import com.catpaw.catpawcore.domain.entity.CommentRecruit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface CommentRecruitQueryRepository {

    Slice<CommentRecruit> findSlicedCommentListByRecruitId(Long recruitId, Pageable pageable);
}
