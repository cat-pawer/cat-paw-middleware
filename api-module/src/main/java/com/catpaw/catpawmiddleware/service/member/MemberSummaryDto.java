package com.catpaw.catpawmiddleware.service.member;

import com.catpaw.catpawcore.domain.eumns.Auth;
import com.catpaw.catpawcore.domain.eumns.SocialType;

public class MemberSummaryDto {

    private Long id;

    private String name;

    private String nickname;

    private String email;

    private SocialType socialType;

    private Auth auth;
}
