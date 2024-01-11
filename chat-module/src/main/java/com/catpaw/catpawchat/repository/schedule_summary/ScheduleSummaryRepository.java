package com.catpaw.catpawchat.repository.schedule_summary;

import com.catpaw.catpawcore.domain.entity.ScheduleSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleSummaryRepository extends JpaRepository<ScheduleSummary, Long> {

    @Modifying
    long deleteByIdIn(List<Long> idList);
}
