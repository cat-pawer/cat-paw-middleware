package com.catpaw.catpawcore.domain.entity;

import com.catpaw.catpawcore.domain.entity.base.BaseEntity;
import com.catpaw.catpawcore.domain.eumns.GroupState;
import com.catpaw.catpawcore.domain.eumns.GroupType;
import com.catpaw.catpawcore.domain.eumns.Scope;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE groups SET is_delete = 'Y' WHERE group_id = ?")
public class Groups extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "GROUP_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR_ID",
            referencedColumnName = "MEMBER_ID",
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Member creator;

    private String name;

    private String detail;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private GroupState state;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private GroupType type;

    private LocalDate endDate;

    public void setId(Long id) {
        this.id = id;
    }

    public void addCreator(Member creator) {
        this.creator = creator;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setState(GroupState state) {
        this.state = state;
    }

    public void setType(GroupType type) {
        this.type = type;
    }
}
