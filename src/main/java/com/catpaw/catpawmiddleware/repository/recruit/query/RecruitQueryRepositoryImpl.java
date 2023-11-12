package com.catpaw.catpawmiddleware.repository.recruit.query;

import com.catpaw.catpawmiddleware.common.factory.dto.CategoryDtoFactory;
import com.catpaw.catpawmiddleware.domain.entity.Category;
import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.domain.eumns.*;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import com.catpaw.catpawmiddleware.repository.condition.RecruitTopicCond;
import com.catpaw.catpawmiddleware.repository.dto.RecruitDetailDto;
import com.catpaw.catpawmiddleware.utils.PageUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.*;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.catpaw.catpawmiddleware.domain.entity.QCategoryMapper.categoryMapper;
import static com.catpaw.catpawmiddleware.domain.entity.QRecruit.recruit;
import static com.catpaw.catpawmiddleware.domain.entity.QCategory.category;

public class RecruitQueryRepositoryImpl implements RecruitQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public RecruitQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<RecruitDetailDto> findRecruitDetailDto(Long recruitId) {
        Assert.notNull(recruitId, "검색 id는 필수입니다.");

        List<Tuple> contents = queryFactory
                .select(recruit, category)
                .from(recruit)
                .where(
                        recruit.id.eq(recruitId)
                )
                .leftJoin(categoryMapper)
                .on(
                        categoryMapper.targetId.eq(recruitId),
                        categoryMapper.type.eq(TargetType.RECRUIT),
                        categoryMapper.isDelete.eq(IsDelete.NO.getValue())
                )
                .leftJoin(category)
                .on(
                        category.id.eq(categoryMapper.category.id),
                        category.isDelete.eq(IsDelete.NO.getValue())
                )
                .fetch();

        if (contents.size() == 0) return Optional.empty();

        return Optional.of(this.createRecruitDetailDto(contents));
    }

    private RecruitDetailDto createRecruitDetailDto(List<Tuple> contents) {
        RecruitDetailDto recruitDetailDto = new RecruitDetailDto();
        for (Tuple content : contents) {
            Recruit findRecruit = content.get(recruit);
            if (recruitDetailDto.getId() == null) recruitDetailDto.copyRecruit(findRecruit);

            Category findCategory = content.get(category);
            if (findCategory == null) continue;

            if (CategoryType.TECH_STACK.equals(findCategory.getType()))
                recruitDetailDto.getTechList().add(CategoryDtoFactory.toCategorySummary(findCategory));
            else if (CategoryType.POSITION.equals(findCategory.getType()))
                recruitDetailDto.getPositionList().add(CategoryDtoFactory.toCategorySummary(findCategory));
            else if (CategoryType.HASH.equals(findCategory.getType()))
                recruitDetailDto.getTagList().add(CategoryDtoFactory.toCategorySummary(findCategory));
        }
        return recruitDetailDto;
    }

    @Override
    public Page<Recruit> findPagedRecruitListWithCategory(RecruitSearchCond searchCond, Pageable pageable) {
        Assert.notNull(searchCond, "검색 조건은 필수입니다.");
        Assert.notNull(searchCond.getRecruitPeriod(), "마감 기한은 필수입니다.");

        List<Recruit> contents = queryFactory
                .select(recruit)
                .from(categoryMapper)
                .innerJoin(recruit)
                .on(
                        categoryMapper.targetId.eq(recruit.id)
                                .and(categoryMapper.type.eq(TargetType.RECRUIT))
                                .and(recruit.isDelete.eq(IsDelete.NO.getValue())))
                .where(
                        new BooleanBuilder()
                                .and(subSelectWhereInCategoryIdOrTagName(searchCond))
                                .or((titleContains(searchCond.getSearchValue())))
                                .and(periodGoe(searchCond.getRecruitPeriod())
                                        .and(onlineTypeEq(searchCond.getOnlineType()))
                                        .and(recruitTypeEq(searchCond.getRecruitType()))
                                        .and(stateEq(searchCond.getState())))
                )
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recruitSort(pageable))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(recruit.count())
                .from(categoryMapper)
                .innerJoin(recruit)
                .on(
                        categoryMapper.targetId.eq(recruit.id)
                                .and(categoryMapper.type.eq(TargetType.RECRUIT))
                                .and(recruit.isDelete.eq(IsDelete.NO.getValue())))
                .where(
                        new BooleanBuilder()
                                .and(subSelectWhereInCategoryIdOrTagName(searchCond))
                                .or((titleContains(searchCond.getSearchValue())))
                                .and(periodGoe(searchCond.getRecruitPeriod())
                                        .and(onlineTypeEq(searchCond.getOnlineType()))
                                        .and(recruitTypeEq(searchCond.getRecruitType()))
                                        .and(stateEq(searchCond.getState())))
                );

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    @Override
    public Slice<Recruit> findSlicedRecruitListWithCategory(RecruitSearchCond searchCond, Pageable pageable) {
        Assert.notNull(searchCond, "검색 조건은 필수입니다.");
        Assert.notNull(searchCond.getRecruitPeriod(), "마감 기한은 필수입니다.");

        List<Recruit> contents = queryFactory
                .select(recruit)
                .from(categoryMapper)
                .innerJoin(recruit)
                .on(
                        categoryMapper.targetId.eq(recruit.id)
                                .and(categoryMapper.type.eq(TargetType.RECRUIT))
                                .and(recruit.isDelete.eq(IsDelete.NO.getValue())))
                .where(
                        new BooleanBuilder()
                                .and(subSelectWhereInCategoryIdOrTagName(searchCond))
                                .or((titleContains(searchCond.getSearchValue())))
                                .and(periodGoe(searchCond.getRecruitPeriod())
                                        .and(onlineTypeEq(searchCond.getOnlineType()))
                                        .and(recruitTypeEq(searchCond.getRecruitType()))
                                        .and(stateEq(searchCond.getState())))
                )
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(recruitSort(pageable))
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
    }

    @Override
    public Page<Recruit> findPagedRecruitForTopic(RecruitTopicCond topicCond, Pageable pageable) {
        Assert.notNull(topicCond, "검색 조건은 필수입니다.");
        Assert.notNull(topicCond.getTopic(), "검색 토픽을 필수입니다.");
        Assert.notNull(topicCond.getRecruitPeriod(), "모집 기간은 필수입니다.");
        Assert.notNull(topicCond.getLimitPeriod(), "조회 범위 기간은 필수입니다.");
        Assert.notNull(topicCond.getState(), "모집 상태는 필수입니다.");

        if (RecruitTopic.DEADLINE.name().equals(topicCond.getTopic().name()))
            return this.findPagedRecruitForDeadLine(topicCond, pageable);
        else if (RecruitTopic.IS_NEW.name().equals(topicCond.getTopic().name()))
            return this.findPagedRecruitForIsNew(topicCond, pageable);
        else throw new IllegalArgumentException("검색 토픽이 올바르지 않습니다.");
    }

    private Page<Recruit> findPagedRecruitForIsNew(RecruitTopicCond topicCond, Pageable pageable) {
        List<Recruit> contents = queryFactory
                .select(recruit)
                .from(recruit)
                .where(
                        recruit.recruitPeriod.goe(topicCond.getRecruitPeriod()),
                        recruit.created.loe(topicCond.getLimitPeriod()),
                        recruit.state.eq(topicCond.getState())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(new OrderSpecifier<>(Order.DESC, recruit.created))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(recruit.count())
                .from(recruit)
                .where(stateEq(topicCond.getState()));

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    private Page<Recruit> findPagedRecruitForDeadLine(RecruitTopicCond topicCond, Pageable pageable) {
        List<Recruit> contents = queryFactory
                .select(recruit)
                .from(recruit)
                .where(
                        recruit.recruitPeriod.goe(topicCond.getRecruitPeriod()),
                        recruit.recruitPeriod.loe(topicCond.getLimitPeriod().toLocalDate()),
                        recruit.state.eq(topicCond.getState())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        new OrderSpecifier<>(Order.ASC, recruit.recruitPeriod),
                        new OrderSpecifier<>(Order.ASC, recruit.created))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(recruit.count())
                .from(recruit)
                .where(
                        recruit.recruitPeriod.goe(topicCond.getRecruitPeriod()),
                        stateEq(topicCond.getState())
                );
        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    @Override
    public Slice<Recruit> findSlicedRecruitForTopic(RecruitTopicCond topicCond, Pageable pageable) {
        Assert.notNull(topicCond, "검색 조건은 필수입니다.");
        Assert.notNull(topicCond.getTopic(), "검색 토픽을 필수입니다.");
        Assert.notNull(topicCond.getRecruitPeriod(), "모집 기간은 필수입니다.");
        Assert.notNull(topicCond.getLimitPeriod(), "조회 범위 기간은 필수입니다.");
        Assert.notNull(topicCond.getState(), "모집 상태는 필수입니다.");

        if (RecruitTopic.DEADLINE.name().equals(topicCond.getTopic().name()))
            return this.findSlicedRecruitForDeadLine(topicCond, pageable);
        else if (RecruitTopic.IS_NEW.name().equals(topicCond.getTopic().name()))
            return this.findSlicedRecruitForIsNew(topicCond, pageable);
        else throw new IllegalArgumentException("검색 토픽이 올바르지 않습니다.");
    }

    private SliceImpl<Recruit> findSlicedRecruitForIsNew(RecruitTopicCond topicCond, Pageable pageable) {
        List<Recruit> contents = queryFactory
                .select(recruit)
                .from(recruit)
                .where(
                        recruit.recruitPeriod.goe(topicCond.getRecruitPeriod()),
                        recruit.created.loe(topicCond.getLimitPeriod()),
                        recruit.state.eq(topicCond.getState())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(new OrderSpecifier<>(Order.DESC, recruit.created))
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
    }

    private SliceImpl<Recruit> findSlicedRecruitForDeadLine(RecruitTopicCond topicCond, Pageable pageable) {
        List<Recruit> contents = queryFactory
                .select(recruit)
                .from(recruit)
                .where(
                        recruit.recruitPeriod.goe(topicCond.getRecruitPeriod()),
                        recruit.recruitPeriod.loe(topicCond.getLimitPeriod().toLocalDate()),
                        recruit.state.eq(topicCond.getState())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(
                        new OrderSpecifier<>(Order.ASC, recruit.recruitPeriod),
                        new OrderSpecifier<>(Order.ASC, recruit.created)
                )
                .fetch();

        return new SliceImpl<>(contents, pageable, PageUtils.getHasNext(contents, pageable));
    }

    private BooleanExpression groupIdEq(Long id) {
        return id != null ? recruit.groups.id.eq(id) : null;
    }

    private BooleanExpression categoryIdIn(List<Long> idList) {
        return idList != null && !idList.isEmpty() ? category.id.in(idList) : null;
    }

    private BooleanExpression titleContains(String title) {
        return StringUtils.hasText(title) ? recruit.title.contains(title) : null;
    }

    private BooleanExpression tagNameContains(String tagName) {
        return StringUtils.hasText(tagName) ? category.name.contains(tagName).and(category.type.eq(CategoryType.HASH)) : null;
    }

    private BooleanExpression periodGoe(LocalDate period) {
        return period != null ? recruit.recruitPeriod.goe(period) : null;
    }

    private BooleanExpression periodEq(LocalDate period) {
        return period != null ? recruit.recruitPeriod.eq(period) : null;
    }

    private BooleanExpression onlineTypeEq(OnlineType onlineType) {
        return onlineType != null ? recruit.onlineType.eq(onlineType) : null;
    }

    private BooleanExpression recruitTypeEq(GroupType recruitType) {
        return recruitType != null ? recruit.groupType.eq(recruitType) : null;
    }

    private BooleanExpression categoryIdEq(Long id) {
        return id != null ? category.id.eq(id) : null;
    }

    private BooleanExpression stateEq(RecruitState state) {
        return state != null ? recruit.state.eq(state) : null;
    }

    private BooleanExpression targetTypeEq(TargetType targetType) {
        return targetType != null ? categoryMapper.type.eq(targetType) : null;
    }

    private BooleanExpression subSelectWhereInCategoryIdOrTagName(RecruitSearchCond searchCond) {
        return (searchCond.getCategoryIdList() == null || searchCond.getCategoryIdList().isEmpty())
                && !StringUtils.hasText(searchCond.getSearchValue()) ?
                null :
                category.id.in(
                JPAExpressions.select(category.id)
                        .from(category)
                        .where(
                                (new BooleanBuilder()
                                        .and(tagNameContains(searchCond.getSearchValue()))
                                        .or(categoryIdIn(searchCond.getCategoryIdList()))
                                )
                        )
                );
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
                    case "commentCount" -> {
                        return new OrderSpecifier<>(direction, recruit.commentCount);
                    }
                }
            }
        }
        return null;
    }
}
