package com.catpaw.catpawmiddleware.repository.groups.query;

import com.catpaw.catpawcore.domain.entity.Groups;
import com.catpaw.catpawcore.domain.eumns.IsDelete;
import com.catpaw.catpawcore.utils.LogUtils;
import com.catpaw.catpawcore.utils.PageUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.*;
import org.springframework.util.Assert;

import java.util.List;

import static com.catpaw.catpawcore.domain.entity.QGroupMember.groupMember;
import static com.catpaw.catpawcore.domain.entity.QGroups.groups;

public class GroupsQueryRepositoryImpl implements GroupsQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public GroupsQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Slice<Groups> findSlicedMyGroups(long memberId, Pageable pageable) {
        Assert.notNull(pageable, LogUtils.notNullFormat("pageable"));

        List<Groups> contents = queryFactory
                .select(groups)
                .from(groups)
                .where(groups.creator.id.eq(memberId))
                .orderBy(new OrderSpecifier<>(Order.DESC, groups.created))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
    }

    @Override
    public Slice<Groups> findSlicedOtherGroups(long memberId, Pageable pageable) {
        Assert.notNull(pageable, LogUtils.notNullFormat("pageable"));

        List<Groups> contents = queryFactory
                .select(groups)
                .from(groupMember)
                .where(
                        groupMember.member.id.eq(memberId),
                        groups.creator.id.ne(memberId)
                )
                .leftJoin(groups)
                .on(
                        groups.id.eq(groupMember.groups.id),
                        groups.isDelete.eq(IsDelete.NO.getValue())
                )
                .orderBy(new OrderSpecifier<>(Order.DESC, groups.created))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
    }
}
