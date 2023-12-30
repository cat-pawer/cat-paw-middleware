package com.catpaw.catpawmiddleware.repository.groups;

import com.catpaw.catpawcore.domain.entity.Groups;
import com.catpaw.catpawcore.utils.LogUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class GroupsRepositoryImpl implements GroupsRepositoryCustom {


    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public GroupsRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Groups getReferenceById(Long groupsId) {
        Assert.notNull(groupsId, LogUtils.notNullFormat("groupsId"));

        return em.getReference(Groups.class, groupsId);
    }
}
