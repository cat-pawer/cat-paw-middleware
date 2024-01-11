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
@SQLDelete(sql = "UPDATE schedule_summary SET is_delete = 'Y' WHERE schedule_summary_id = ?")
public class ScheduleSummary extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "schedule_summary_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_MEMBER_ID",
            referencedColumnName = "MEMBER_ID",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member cMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_ID")
    private Schedule schedule;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long statusCodeId;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addCMember(Member cMember) {
        this.cMember = cMember;
    }

    public void addSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStatusCodeId(Long statusId) {
        this.statusCodeId = statusId;
    }
}
