package com.catpaw.catpawmiddleware.repository.member;

import com.catpaw.catpawmiddleware.domain.entity.Member;
import com.catpaw.catpawmiddleware.utils.LogUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Member getReferenceById(Long id) {
        Assert.notNull(id, LogUtils.notNullFormat("memberId"));

        return em.getReference(Member.class, id);
    }
}
