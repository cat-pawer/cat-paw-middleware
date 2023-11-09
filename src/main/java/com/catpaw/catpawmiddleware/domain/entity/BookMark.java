package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.entity.base.BaseEntity;
import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE bookmark SET is_delete = 'Y' WHERE bookmark_id = ?")
public class BookMark extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "BOOKMARK_ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private Long targetId;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TargetType type;
}

