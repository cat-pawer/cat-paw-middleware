package com.catpaw.catpawcore.repository.member;

import com.catpaw.catpawcore.domain.entity.Member;

public interface MemberRepositoryCustom {

    Member getReferenceById(Long id);
}