package com.catpaw.catpawmiddleware.repository.member;

import com.catpaw.catpawcore.domain.entity.Member;
import com.catpaw.catpawcore.domain.eumns.Auth;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByEmailAndAuth(String email, Auth auth);

    List<Member> findByAuthAndIdIn(Auth auth, List<Long> idList);
}
