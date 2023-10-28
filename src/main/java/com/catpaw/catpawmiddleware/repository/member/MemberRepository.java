package com.catpaw.catpawmiddleware.repository.member;

import com.catpaw.catpawmiddleware.domain.entity.Member;
import com.catpaw.catpawmiddleware.domain.eumns.Auth;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByName(String name);

    Optional<Member> findByEmailAndAuth(String email, Auth auth);
}
