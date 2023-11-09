package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.entity.base.BaseEntity;
import com.catpaw.catpawmiddleware.domain.eumns.GroupState;
import com.catpaw.catpawmiddleware.domain.eumns.GroupType;
import com.catpaw.catpawmiddleware.domain.eumns.Scope;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE groups SET is_delete = 'Y' WHERE group_id = ?")
public class Groups extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "GROUP_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String name;

    private String detail;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private GroupState state;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private GroupType type;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private Scope scope;

}
