package com.catpaw.catpawcore.domain.entity;

import com.catpaw.catpawcore.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE group_board SET is_delete = 'Y' WHERE group_board_id = ?")
public class GroupBoard extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "GROUP_BOARD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Groups groups;

    private String title;

    private String content;

    private Long likeCount;

    public void setId(Long id) {
        this.id = id;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
}
