package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE comment_recruit SET is_delete = 'Y' WHERE comment_recruit_id = ?")
public class CommentRecruit extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "COMMENT_RECRUIT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECRUIT_ID")
    private Recruit recruit;

    private String content;

    public void setId(Long id) {
        this.id = id;
    }

    public void changeMember(Member member) {
        this.member = member;
    }

    public void changeRecruit(Recruit recruit) {
        this.recruit = recruit;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
