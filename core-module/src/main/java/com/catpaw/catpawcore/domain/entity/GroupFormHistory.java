package com.catpaw.catpawcore.domain.entity;

import com.catpaw.catpawcore.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE group_form_history SET is_delete = 'Y' WHERE group_form_history_id = ?")
public class GroupFormHistory extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "GROUP_FORM_HISTORY")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Groups groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_MEMBER_ID")
    private GroupMember groupMember;

    private String content;
}
