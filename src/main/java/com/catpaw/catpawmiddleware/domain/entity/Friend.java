package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.FriendState;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE friend SET is_delete = 'Y' WHERE friend_id = ?")
public class Friend extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "FRIEND_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_MEMBER_ID")
    private Member fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECOND_MEMBER_ID")
    private Member toMember;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private FriendState state;
}
