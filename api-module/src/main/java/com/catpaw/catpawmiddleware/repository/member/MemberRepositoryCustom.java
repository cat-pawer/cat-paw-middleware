package com.catpaw.catpawmiddleware.repository.member;

import com.catpaw.catpawcore.domain.entity.Member;

public interface MemberRepositoryCustom {

    Member getReferenceById(Long id);
}