package com.catpaw.catpawcore.domain.entity;

import com.catpaw.catpawcore.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE schedule_detail SET is_delete = 'Y' WHERE schedule_detail_id = ?")
public class ScheduleDetail extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "schedule_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_SUMMARY_ID")
    private ScheduleSummary scheduleSummary;

    @Column(columnDefinition = "TEXT")
    private String content;

    public void setId(Long id) {
        this.id = id;
    }

    public void setScheduleSummary(ScheduleSummary scheduleSummary) {
        this.scheduleSummary = scheduleSummary;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
