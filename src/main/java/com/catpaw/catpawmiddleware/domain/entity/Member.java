package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.Auth;
import com.catpaw.catpawmiddleware.domain.eumns.SocialType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "is_delete = 'N'")
@SQLDelete(sql = "UPDATE member SET is_delete = 'Y' WHERE member_id = ?")
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    private String nickname;

    private String email;

    private String password;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private Auth auth;

    private String phone;

    private String birth;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSocialType(SocialType socialType) {
        this.socialType = socialType;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
