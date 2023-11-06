package com.catpaw.catpawmiddleware.repository.category;

import com.catpaw.catpawmiddleware.domain.entity.CategoryMapper;
import com.catpaw.catpawmiddleware.domain.entity.QCategory;
import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

import static com.catpaw.catpawmiddleware.domain.entity.QCategoryMapper.categoryMapper;
import static com.catpaw.catpawmiddleware.domain.entity.QRecruit.recruit;

@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private final CategoryMapperRepository categoryMapperRepository;

    public CategoryRepositoryImpl(EntityManager em, CategoryMapperRepository categoryMapperRepository) {
        this.em = em;
        this.categoryMapperRepository = categoryMapperRepository;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public CategoryMapper save(CategoryMapper categoryMapper) {
        return categoryMapperRepository.save(categoryMapper);
    }


}
