package com.catpaw.catpawmiddleware.repository.friend.query;

import com.catpaw.catpawmiddleware.domain.entity.Friend;
import com.catpaw.catpawmiddleware.domain.entity.QMember;
import com.catpaw.catpawmiddleware.domain.eumns.FriendState;
import com.catpaw.catpawmiddleware.repository.condition.FriendSearchCond;
import com.catpaw.catpawmiddleware.utils.PageUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.*;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.catpaw.catpawmiddleware.domain.entity.QFriend.friend;
import static com.catpaw.catpawmiddleware.domain.entity.QMember.member;

@Repository
public class FriendQueryRepositoryImpl implements FriendQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public FriendQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Friend> findFriendSummaryPage(FriendSearchCond searchCond, Pageable pageable) {
        QMember fromMember = new QMember("fromMember");
        QMember toMember = new QMember("toMember");

        List<Friend> contents = queryFactory
                .select(friend)
                .from(friend)
                .innerJoin(friend.fromMember, fromMember)
                .innerJoin(friend.toMember, toMember)
                .fetchJoin()
                .where(
                        fromMemberIdEq(searchCond.getMemberId()).or(toMemberIdEq(searchCond.getMemberId())),
                        friendStateEq(searchCond.getState()),
                        memberNameEq(searchCond.getName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(friendSort(pageable))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(friend.count())
                .from(friend)
                .innerJoin(friend.fromMember, fromMember)
                .innerJoin(friend.toMember, toMember)
                .where(
                        fromMemberIdEq(searchCond.getMemberId()).or(toMemberIdEq(searchCond.getMemberId())),
                        friendStateEq(searchCond.getState()),
                        memberNameEq(searchCond.getName())
                );

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    @Override
    public Slice<Friend> findFriendSummarySlice(FriendSearchCond searchCond, Pageable pageable) {
        QMember fromMember = new QMember("fromMember");
        QMember toMember = new QMember("toMember");

        List<Friend> contents = queryFactory
                .select(friend)
                .from(friend)
                .innerJoin(friend.fromMember, fromMember)
                .innerJoin(friend.toMember, toMember)
                .fetchJoin()
                .where(
                        fromMemberIdEq(searchCond.getMemberId()).or(toMemberIdEq(searchCond.getMemberId())),
                        friendStateEq(searchCond.getState()),
                        memberNameEq(searchCond.getName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(friendSort(pageable))
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
    }

    @Override
    public Page<Friend> findMyRequestFriendSummaryPage(FriendSearchCond searchCond, Pageable pageable) {
        QMember fromMember = new QMember("fromMember");
        QMember toMember = new QMember("toMember");

        List<Friend> contents = queryFactory
                .select(friend)
                .from(friend)
                .innerJoin(friend.fromMember, fromMember)
                .innerJoin(friend.toMember, toMember)
                .fetchJoin()
                .where(
                        fromMemberIdEq(searchCond.getMemberId()),
                        friendStateEq(searchCond.getState()),
                        memberNameEq(searchCond.getName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(friendSort(pageable))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(friend.count())
                .from(friend)
                .innerJoin(friend.fromMember, fromMember)
                .innerJoin(friend.toMember, toMember)
                .where(
                        fromMemberIdEq(searchCond.getMemberId()),
                        friendStateEq(searchCond.getState()),
                        memberNameEq(searchCond.getName())
                );

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    @Override
    public Slice<Friend> findMyRequestFriendSummarySlice(FriendSearchCond searchCond, Pageable pageable) {
        QMember fromMember = new QMember("fromMember");
        QMember toMember = new QMember("toMember");

        List<Friend> contents = queryFactory
                .select(friend)
                .from(friend)
                .innerJoin(friend.fromMember, fromMember)
                .innerJoin(friend.toMember, toMember)
                .fetchJoin()
                .where(
                        fromMemberIdEq(searchCond.getMemberId()),
                        friendStateEq(searchCond.getState()),
                        memberNameEq(searchCond.getName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(friendSort(pageable))
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
    }

    @Override
    public Page<Friend> findOtherRequestFriendSummaryPage(FriendSearchCond searchCond, Pageable pageable) {
        QMember fromMember = new QMember("fromMember");
        QMember toMember = new QMember("toMember");

        List<Friend> contents = queryFactory
                .select(friend)
                .from(friend)
                .innerJoin(friend.fromMember, fromMember)
                .innerJoin(friend.toMember, toMember)
                .fetchJoin()
                .where(
                        fromMemberIdEq(searchCond.getMemberId()),
                        friendStateEq(searchCond.getState()),
                        memberNameEq(searchCond.getName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(friendSort(pageable))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(friend.count())
                .from(friend)
                .innerJoin(friend.fromMember, fromMember)
                .innerJoin(friend.toMember, toMember)
                .where(
                        toMemberIdEq(searchCond.getMemberId()),
                        friendStateEq(searchCond.getState()),
                        memberNameEq(searchCond.getName())
                );

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    @Override
    public Slice<Friend> findOtherRequestFriendSummarySlice(FriendSearchCond searchCond, Pageable pageable) {
        QMember fromMember = new QMember("fromMember");
        QMember toMember = new QMember("toMember");

        List<Friend> contents = queryFactory
                .select(friend)
                .from(friend)
                .innerJoin(friend.fromMember, fromMember)
                .innerJoin(friend.toMember, toMember)
                .fetchJoin()
                .where(
                        toMemberIdEq(searchCond.getMemberId()),
                        friendStateEq(searchCond.getState()),
                        memberNameEq(searchCond.getName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(friendSort(pageable))
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
    }

    private BooleanExpression fromMemberIdEq(Long id) {
        return id != null ? friend.fromMember.id.eq(id) : null;
    }

    private BooleanExpression toMemberIdEq(Long id) {
        return id != null ? friend.toMember.id.eq(id) : null;
    }


    private BooleanExpression friendStateEq(FriendState state) {
        return state != null ? friend.state.eq(state) : null;
    }

    private BooleanExpression memberNameEq(String name) {
        return StringUtils.hasText(name) ? member.name.eq(name) : null;
    }

    private OrderSpecifier<?> friendSort(Pageable page) {
        if (!page.getSort().isEmpty()) {
            for (Sort.Order order : page.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "updated" -> {
                        return new OrderSpecifier<>(direction, friend.updated);
                    }
                    case "created" -> {
                        return new OrderSpecifier<>(direction, friend.created);
                    }
                }
            }
        }
        return null;
    }
}
