package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.entity.base.BaseEntity;
import com.catpaw.catpawmiddleware.domain.eumns.Scope;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE member_detail SET is_delete = 'Y' WHERE member_detail_id = ?")
public class MemberDetail extends BaseEntity {

    @Id
    @Column(name = "MEMBER_DETAIL_ID")
    private Long id;

    private Long reliability;

    private String mainPortfolioLink;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private Scope scope;

    @MapsId
    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

}
