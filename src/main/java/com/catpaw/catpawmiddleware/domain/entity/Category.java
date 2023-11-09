package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.entity.base.BaseEntity;
import com.catpaw.catpawmiddleware.domain.eumns.CategoryType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE category SET is_delete = 'Y' WHERE category_id = ?")
public class Category extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    private String name;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private CategoryType type;

    private String value;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
