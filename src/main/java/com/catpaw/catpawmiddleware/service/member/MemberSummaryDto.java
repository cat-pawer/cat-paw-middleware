package com.catpaw.catpawmiddleware.service.member;

import com.catpaw.catpawmiddleware.domain.eumns.Auth;
import com.catpaw.catpawmiddleware.domain.eumns.SocialType;

public class MemberSummaryDto {

    private Long id;

    private String name;

    private String nickname;

    private String email;

    private SocialType socialType;

    private Auth auth;
}