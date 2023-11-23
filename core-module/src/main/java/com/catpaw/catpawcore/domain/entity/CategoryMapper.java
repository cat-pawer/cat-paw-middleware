package com.catpaw.catpawcore.domain.entity;

import com.catpaw.catpawcore.domain.entity.base.BaseEntity;
import com.catpaw.catpawcore.domain.eumns.TargetType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE category_mapper SET is_delete = 'Y' WHERE category_mapper_id = ?")
public class CategoryMapper extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "CATEGORY_MAPPER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    private Long targetId;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TargetType type;

    public void addCategory(Category category) {
        this.category = category;
    }

    public void addTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public void addType(TargetType type) {
        this.type = type;
    }
}