package com.catpaw.catpawcore.domain.entity;

import com.catpaw.catpawcore.domain.entity.base.BaseEntity;
import com.catpaw.catpawcore.domain.eumns.Auth;
import com.catpaw.catpawcore.domain.eumns.GroupMemberState;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE group_member SET is_delete = 'Y' WHERE group_member_id = ?")
public class GroupMember extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "GROUP_MEMBER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Groups groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POSITION_ID",
            referencedColumnName = "CATEGORY_ID",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Category position;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private Auth auth;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private GroupMemberState state;
}
