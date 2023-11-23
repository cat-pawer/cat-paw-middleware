package com.catpaw.catpawmiddleware.repository.comment.query;

import com.catpaw.catpawcore.domain.entity.CommentRecruit;
import com.catpaw.catpawcore.domain.eumns.IsDelete;
import com.catpaw.catpawcore.utils.LogUtils;
import com.catpaw.catpawcore.utils.PageUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

import static com.catpaw.catpawcore.domain.entity.QCommentRecruit.commentRecruit;
import static com.catpaw.catpawcore.domain.entity.QRecruit.recruit;

@Repository
public class CommentRecruitQueryRepositoryImpl implements CommentRecruitQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CommentRecruitQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Slice<CommentRecruit> findSlicedCommentListByRecruitId(Long recruitId, Pageable pageable) {
        Assert.notNull(recruitId, LogUtils.notNullFormat("recruitId"));

        List<CommentRecruit> contents = queryFactory
                .select(commentRecruit)
                .from(commentRecruit)
                .innerJoin(recruit)
                .on(
                        commentRecruit.recruit.id.eq(recruit.id),
                        recruit.isDelete.eq(IsDelete.NO.getValue())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(commentSort(pageable))
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
    }

    private OrderSpecifier<?> commentSort(Pageable page) {
        if (!page.getSort().isEmpty()) {
            for (Sort.Order order : page.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "updated" -> {
                        return new OrderSpecifier<>(direction, commentRecruit.updated);
                    }
                    case "created" -> {
                        return new OrderSpecifier<>(direction, commentRecruit.created);
                    }
                }
            }
        }
        return null;
    }
}
