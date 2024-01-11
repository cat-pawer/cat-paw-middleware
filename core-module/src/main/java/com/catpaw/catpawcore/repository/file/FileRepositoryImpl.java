package com.catpaw.catpawcore.repository.file;

import com.catpaw.catpawcore.domain.dto.repository.PortFolioDto;
import com.catpaw.catpawcore.domain.entity.FileMaster;
import com.catpaw.catpawcore.domain.dto.service.file.FileTarget;
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

    @Override
    public PortFolioDto findMainPortFolio(long memberId) {
        return em.createQuery("SELECT " +
                        "new com.catpaw.catpawcore.domain.dto.repository.PortFolioDto" +
                        "(member.id, fileMaster.id, fileMaster.absoluteDestination, fileMaster.fileOriginalName, fileMaster.created, fileMaster.updated) " +
                        "FROM Member member " +
                        "LEFT JOIN FileMaster fileMaster " +
                        "ON fileMaster.targetId = member.mainPortfolioId " +
                        "AND fileMaster.type = com.catpaw.catpawcore.domain.eumns.TargetType.PORTFOLIO " +
                        "AND fileMaster.isDelete = 'N' " +
                        "WHERE member.id = :memberId", PortFolioDto.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }
}
