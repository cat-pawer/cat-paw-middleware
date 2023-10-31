package com.catpaw.catpawmiddleware.repository.recruit.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class RecruitQueryRepositoryImpl implements RecruitQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public RecruitQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
}
