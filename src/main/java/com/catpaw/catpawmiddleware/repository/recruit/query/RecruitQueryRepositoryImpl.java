package com.catpaw.catpawmiddleware.repository.recruit.query;

import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.domain.eumns.OnlineType;
import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import com.catpaw.catpawmiddleware.utils.PageUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.*;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static com.catpaw.catpawmiddleware.domain.entity.QRecruit.recruit;

public class RecruitQueryRepositoryImpl implements RecruitQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public RecruitQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Recruit> findPagedRecruitList(RecruitSearchCond searchCond, Pageable pageable) {
        List<Recruit> contents = queryFactory
                .selectFrom(recruit)
                .where(
                        recruitIdEq(searchCond.getRecruitId()),
                        groupIdEq(searchCond.getGroupId()),
                        titleEq(searchCond.getTitle()),
                        periodEq(searchCond.getRecruitPeriod()),
                        startDateEq(searchCond.getStartDate()),
                        endDateEq(searchCond.getEndDate()),
                        onlineTypeEq(searchCond.getOnlineType()),
                        stateEq(searchCond.getState())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recruitSort(pageable))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(recruit.count())
                .from(recruit)
                .where(
                        recruitIdEq(searchCond.getRecruitId()),
                        groupIdEq(searchCond.getGroupId()),
                        titleEq(searchCond.getTitle()),
                        periodEq(searchCond.getRecruitPeriod()),
                        startDateEq(searchCond.getStartDate()),
                        endDateEq(searchCond.getEndDate()),
                        onlineTypeEq(searchCond.getOnlineType()),
                        stateEq(searchCond.getState())
                );

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    @Override
    public Slice<Recruit> findSlicedRecruitList(RecruitSearchCond searchCond, Pageable pageable) {
        List<Recruit> contents = queryFactory
                .selectFrom(recruit)
                .where(
                        recruitIdEq(searchCond.getRecruitId()),
                        groupIdEq(searchCond.getGroupId()),
                        titleEq(searchCond.getTitle()),
                        periodEq(searchCond.getRecruitPeriod()),
                        startDateEq(searchCond.getStartDate()),
                        endDateEq(searchCond.getEndDate()),
                        onlineTypeEq(searchCond.getOnlineType()),
                        stateEq(searchCond.getState())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(recruitSort(pageable))
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
    }


    private BooleanExpression recruitIdEq(Long id) {
        return id != null ? recruit.id.eq(id) : null;
    }

    private BooleanExpression groupIdEq(Long id) {
        return id != null ? recruit.groups.id.eq(id) : null;
    }

    private BooleanExpression titleEq(String title) {
        return StringUtils.hasText(title) ? recruit.title.eq(title) : null;
    }

    private BooleanExpression periodEq(LocalDate period) {
        return period != null ? recruit.recruitPeriod.eq(period) : null;
    }

    private BooleanExpression startDateEq(LocalDate startDate) {
        return startDate != null ? recruit.startDate.eq(startDate) : null;
    }

    private BooleanExpression endDateEq(LocalDate endDate) {
        return endDate != null ? recruit.endDate.eq(endDate) : null;
    }

    private BooleanExpression onlineTypeEq(OnlineType onlineType) {
        return onlineType != null ? recruit.onlineType.eq(onlineType) : null;
    }

    private BooleanExpression stateEq(RecruitState state) {
        return state != null ? recruit.state.eq(state) : null;
    }

    private OrderSpecifier<?> recruitSort(Pageable page) {
        if (!page.getSort().isEmpty()) {
            for (Sort.Order order : page.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "updated" -> {
                        return new OrderSpecifier<>(direction, recruit.updated);
                    }
                    case "created" -> {
                        return new OrderSpecifier<>(direction, recruit.created);
                    }
                    case "recruitPeriod" -> {
                        return new OrderSpecifier<>(direction, recruit.recruitPeriod);
                    }
                    case "viewCount" -> {
                        return new OrderSpecifier<>(direction, recruit.viewCount);
                    }
//                    case "commentCount" -> {
//                        return new OrderSpecifier<>(direction, recruit.commentCount);
//                    }
                }
            }
        }
        return null;
    }
}
