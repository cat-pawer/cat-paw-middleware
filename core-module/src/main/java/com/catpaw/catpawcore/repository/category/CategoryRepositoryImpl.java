package com.catpaw.catpawcore.repository.category;

import com.catpaw.catpawcore.domain.entity.CategoryMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;


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
