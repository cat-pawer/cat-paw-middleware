package com.catpaw.catpawmiddleware.repository.recruit.query;

import com.catpaw.catpawmiddleware.controller.request.search.SearchForm;
import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.domain.eumns.GroupType;
import com.catpaw.catpawmiddleware.domain.eumns.OnlineType;
import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import com.catpaw.catpawmiddleware.repository.condition.RecruitTopicCond;
import com.catpaw.catpawmiddleware.utils.PageUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.*;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static com.catpaw.catpawmiddleware.domain.entity.QCategoryMapper.categoryMapper;
import static com.catpaw.catpawmiddleware.domain.entity.QRecruit.recruit;
import static com.catpaw.catpawmiddleware.domain.entity.QTagRecruit.tagRecruit;

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
                        groupIdEq(searchCond.getGroupId()),
                        titleEq(searchCond.getTitle()),
                        periodEq(searchCond.getRecruitPeriod()),
                        startDateEq(searchCond.getStartDate()),
                        endDateEq(searchCond.getEndDate()),
                        onlineTypeEq(searchCond.getOnlineType()),
                        stateEq(searchCond.getState()),
                        recruitTypeEq(searchCond.getRecruitType())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recruitSort(pageable))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(recruit.count())
                .from(recruit)
                .where(
                        groupIdEq(searchCond.getGroupId()),
                        titleEq(searchCond.getTitle()),
                        periodEq(searchCond.getRecruitPeriod()),
                        startDateEq(searchCond.getStartDate()),
                        endDateEq(searchCond.getEndDate()),
                        onlineTypeEq(searchCond.getOnlineType()),
                        stateEq(searchCond.getState()),
                        recruitTypeEq(searchCond.getRecruitType())
                );

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    @Override
    public Slice<Recruit> findSlicedRecruitList(RecruitSearchCond searchCond, Pageable pageable) {
        List<Recruit> contents = queryFactory
                .selectFrom(recruit)
                .where(
                        groupIdEq(searchCond.getGroupId()),
                        titleEq(searchCond.getTitle()),
                        periodEq(searchCond.getRecruitPeriod()),
                        startDateEq(searchCond.getStartDate()),
                        endDateEq(searchCond.getEndDate()),
                        onlineTypeEq(searchCond.getOnlineType()),
                        stateEq(searchCond.getState()),
                        recruitTypeEq(searchCond.getRecruitType())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(recruitSort(pageable))
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
    }

    @Override
    public Page<Recruit> findPagedRecruitListForTag(RecruitSearchCond searchCond, Pageable pageable) {
        Assert.notNull(searchCond.getTagId(), "태그 아이디는 필수 값입니다.");

        List<Recruit> contents = queryFactory
                .select(recruit)
                .from(tagRecruit)
                .innerJoin(recruit).fetchJoin()
                .where(
                        tagIdEq(searchCond.getTagId()),
                        groupIdEq(searchCond.getGroupId()),
                        titleEq(searchCond.getTitle()),
                        periodEq(searchCond.getRecruitPeriod()),
                        startDateEq(searchCond.getStartDate()),
                        endDateEq(searchCond.getEndDate()),
                        onlineTypeEq(searchCond.getOnlineType()),
                        stateEq(searchCond.getState()),
                        recruitTypeEq(searchCond.getRecruitType())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recruitSort(pageable))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(recruit.count())
                .from(tagRecruit)
                .where(
                        tagIdEq(searchCond.getTagId()),
                        groupIdEq(searchCond.getGroupId()),
                        titleEq(searchCond.getTitle()),
                        periodEq(searchCond.getRecruitPeriod()),
                        startDateEq(searchCond.getStartDate()),
                        endDateEq(searchCond.getEndDate()),
                        onlineTypeEq(searchCond.getOnlineType()),
                        stateEq(searchCond.getState()),
                        recruitTypeEq(searchCond.getRecruitType())
                );

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    @Override
    public Slice<Recruit> findSlicedRecruitListForTag(RecruitSearchCond searchCond, Pageable pageable) {
        Assert.notNull(searchCond.getTagId(), "태그 아이디는 필수 값입니다.");

        List<Recruit> contents = queryFactory
                .select(recruit)
                .from(tagRecruit)
                .innerJoin(recruit).fetchJoin()
                .where(
                        tagIdEq(searchCond.getTagId()),
                        groupIdEq(searchCond.getGroupId()),
                        titleEq(searchCond.getTitle()),
                        periodEq(searchCond.getRecruitPeriod()),
                        startDateEq(searchCond.getStartDate()),
                        endDateEq(searchCond.getEndDate()),
                        onlineTypeEq(searchCond.getOnlineType()),
                        stateEq(searchCond.getState()),
                        recruitTypeEq(searchCond.getRecruitType())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(recruitSort(pageable))
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
    }

    @Override
    public Page<Recruit> findPagedRecruitListForCategory(RecruitSearchCond searchCond, Pageable pageable) {
        Assert.notNull(searchCond.getCategoryId(), "카테고리 아이디는 필수입니다.");
        Assert.notNull(searchCond.getTargetType(), "카테고리 대상 타입은 필수입니다.");

        List<Recruit> contents = queryFactory
                .select(recruit)
                .from(categoryMapper)
                .innerJoin(recruit).on(categoryMapper.targetId.eq(recruit.id))
                .where(
                        categoryIdEq(searchCond.getCategoryId()),
                        targetTypeEq(searchCond.getTargetType()),
                        groupIdEq(searchCond.getGroupId()),
                        titleEq(searchCond.getTitle()),
                        periodEq(searchCond.getRecruitPeriod()),
                        startDateEq(searchCond.getStartDate()),
                        endDateEq(searchCond.getEndDate()),
                        onlineTypeEq(searchCond.getOnlineType()),
                        stateEq(searchCond.getState()),
                        recruitTypeEq(searchCond.getRecruitType())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recruitSort(pageable))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(recruit.count())
                .from(categoryMapper)
                .innerJoin(recruit).on(categoryMapper.targetId.eq(recruit.id))
                .where(
                        categoryIdEq(searchCond.getCategoryId()),
                        targetTypeEq(searchCond.getTargetType()),
                        groupIdEq(searchCond.getGroupId()),
                        titleEq(searchCond.getTitle()),
                        periodEq(searchCond.getRecruitPeriod()),
                        startDateEq(searchCond.getStartDate()),
                        endDateEq(searchCond.getEndDate()),
                        onlineTypeEq(searchCond.getOnlineType()),
                        stateEq(searchCond.getState()),
                        recruitTypeEq(searchCond.getRecruitType())
                );

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    @Override
    public Slice<Recruit> findSlicedRecruitListForCategory(RecruitSearchCond searchCond, Pageable pageable) {
        List<Recruit> contents = queryFactory
                .select(recruit)
                .from(categoryMapper)
                .innerJoin(recruit).on(categoryMapper.targetId.eq(recruit.id))
                .where(
                        categoryIdEq(searchCond.getCategoryId()),
                        targetTypeEq(searchCond.getTargetType()),
                        groupIdEq(searchCond.getGroupId()),
                        titleEq(searchCond.getTitle()),
                        periodEq(searchCond.getRecruitPeriod()),
                        startDateEq(searchCond.getStartDate()),
                        endDateEq(searchCond.getEndDate()),
                        onlineTypeEq(searchCond.getOnlineType()),
                        stateEq(searchCond.getState()),
                        recruitTypeEq(searchCond.getRecruitType())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(recruitSort(pageable))
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
    }

    @Override
    public Page<Recruit> findPagedRecruitForTopic(RecruitTopicCond searchCond, Pageable pageable) {
        Assert.notNull(searchCond, "RecruitTopicCond는 필수입니다.");
        Assert.notNull(searchCond.getTopic(), "검색 토픽을 필수입니다.");
        if (searchCond.getTopic().equals(SearchForm.DEADLINE.getValue())) {
            return findPagedRecruitForDeadline(searchCond, pageable);
        }
        else if (searchCond.getTopic().equals(SearchForm.ISNEW.getValue())) {
            return findPagedRecruitForIsNew(searchCond, pageable);
        }
        else {
            throw new IllegalArgumentException("검색 토픽이 올바르지 않습니다.");
        }
    }

    private Page<Recruit> findPagedRecruitForIsNew(RecruitTopicCond searchCond, Pageable pageable) {
        List<Recruit> contents = queryFactory
                .select(recruit)
                .from(recruit)
                .where(stateEq(searchCond.getState()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(new OrderSpecifier<>(Order.DESC, recruit.created))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(recruit.count())
                .from(recruit)
                .where(stateEq(searchCond.getState()));

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    private Page<Recruit> findPagedRecruitForDeadline(RecruitTopicCond searchCond, Pageable pageable) {
        Assert.notNull(searchCond.getRecruitPeriod(), "마감 기준 기한은 필수입니다.");

        List<Recruit> contents = queryFactory
                .select(recruit)
                .from(recruit)
                .where(
                        recruit.recruitPeriod.goe(searchCond.getRecruitPeriod()),
                        stateEq(searchCond.getState())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(new OrderSpecifier<>(Order.ASC, recruit.recruitPeriod))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(recruit.count())
                .from(recruit)
                .where(
                        recruit.recruitPeriod.goe(searchCond.getRecruitPeriod()),
                        stateEq(searchCond.getState())
                );
        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    @Override
    public Slice<Recruit> findSlicedRecruitForTopic(RecruitTopicCond searchCond, Pageable pageable) {
        Assert.notNull(searchCond, "RecruitTopicCond는 필수입니다.");
        Assert.notNull(searchCond.getTopic(), "검색 토픽을 필수입니다.");
        if (searchCond.getTopic().equals(SearchForm.DEADLINE.getValue())) {
            return findSlicedRecruitForDeadline(searchCond, pageable);
        }
        else if (searchCond.getTopic().equals(SearchForm.ISNEW.getValue())) {
            return findSlicedRecruitForIsNew(searchCond, pageable);
        }
        else {
            throw new IllegalArgumentException("검색 토픽이 올바르지 않습니다.");
        }
    }

    private SliceImpl<Recruit> findSlicedRecruitForIsNew(RecruitTopicCond searchCond, Pageable pageable) {
        List<Recruit> contents = queryFactory
                .select(recruit)
                .from(recruit)
                .where(stateEq(searchCond.getState()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(new OrderSpecifier<>(Order.DESC, recruit.created))
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
    }

    private SliceImpl<Recruit> findSlicedRecruitForDeadline(RecruitTopicCond searchCond, Pageable pageable) {
        List<Recruit> contents = queryFactory
                .select(recruit)
                .from(recruit)
                .where(
                        recruit.recruitPeriod.goe(searchCond.getRecruitPeriod()),
                        stateEq(searchCond.getState())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(new OrderSpecifier<>(Order.ASC, recruit.recruitPeriod))
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
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

    private BooleanExpression recruitTypeEq(GroupType recruitType) {
        return recruitType != null ? recruit.groupType.eq(recruitType) : null;
    }

    private BooleanExpression categoryIdEq(Long id) {
        return id != null ? categoryMapper.category.id.eq(id) : null;
    }

    private BooleanExpression tagIdEq(Long id) {
        return id != null ? tagRecruit.tag.id.eq(id) : null;
    }

    private BooleanExpression stateEq(RecruitState state) {
        return state != null ? recruit.state.eq(state) : null;
    }

    private BooleanExpression targetTypeEq(TargetType targetType) {
        return targetType != null ? categoryMapper.type.eq(targetType) : null;
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
