package com.catpaw.catpawmiddleware.repository.member;

import com.catpaw.catpawmiddleware.domain.entity.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {


    Member getReferenceById(Long id);
}