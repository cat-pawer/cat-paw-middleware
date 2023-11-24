package com.catpaw.catpawchat.repository.group;

import com.catpaw.catpawcore.domain.entity.Groups;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupRepositoryImpl implements GroupRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public GroupRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Groups> findGroupListByMemberId(Long memberId) {
        return em.createQuery("SELECT groups " +
                        "FROM GroupMember groupMember " +
                        "JOIN FETCH Groups groups " +
                        "JOIN FETCH Member member " +
                        "WHERE groupMember.member.id = :memberId " +
                        "AND groupMember.groups.state = com.catpaw.catpawcore.domain.eumns.GroupState.ACTIVE",
                        Groups.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
