package com.catpaw.catpawmiddleware.repository.file;

import com.catpaw.catpawcore.domain.entity.FileMaster;
import com.catpaw.catpawmiddleware.service.dto.file.FileTarget;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileRepositoryImpl implements FileRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public FileRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<FileMaster> findFileMasterByFileTarget(FileTarget fileTarget) {
        return em.createQuery("SELECT fileMaster " +
                        "FROM FileMaster fileMaster " +
                        "WHERE fileMaster.targetId = :targetId " +
                        "AND fileMaster.type = :targetType", FileMaster.class)
                .setParameter("targetId", fileTarget.targetId())
                .setParameter("targetType", fileTarget.targetType())
                .getResultList();
    }
}
