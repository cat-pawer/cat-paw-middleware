package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE group_form_column SET is_delete = 'Y' WHERE group_form_column_id = ?")
public class GroupFormColumn extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "GROUP_FORM_COLUMN_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Groups groups;

    private String columnKey;

    private String columnValue;

}
