package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.GroupType;
import com.catpaw.catpawmiddleware.domain.eumns.OnlineType;
import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE recruit SET is_delete = 'Y' WHERE recruit_id = ?")
public class Recruit extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "RECRUIT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Groups groups;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String contact;

    private GroupType groupType;

    private LocalDate recruitPeriod;

    private Integer peopleNumber;

    private Integer expectDuration;

    private Integer viewCount;

    private Integer commentCount;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private OnlineType onlineType;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private RecruitState state;

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public void setRecruitPeriod(LocalDate recruitPeriod) {
        this.recruitPeriod = recruitPeriod;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public void setExpectDuration(Integer expectDuration) { this.expectDuration = expectDuration; }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public void setOnlineType(OnlineType onlineType) {
        this.onlineType = onlineType;
    }

    public void setState(RecruitState state) {
        this.state = state;
    }
}
