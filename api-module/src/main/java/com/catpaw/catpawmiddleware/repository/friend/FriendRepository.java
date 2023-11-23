package com.catpaw.catpawmiddleware.repository.friend;

import com.catpaw.catpawcore.domain.entity.Friend;
import com.catpaw.catpawmiddleware.repository.friend.query.FriendQueryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Long>, FriendRepositoryCustom, FriendQueryRepository {


    @Query("SELECT friend FROM Friend friend " +
            "WHERE (friend.fromMember.id = :fromMemberId AND friend.toMember.id = :toMemberId)" +
            "OR friend.fromMember.id = :toMemberId AND friend.toMember.id = :fromMemberId")
    Optional<Friend> findDuplicateFriendRequest(@Param("fromMemberId") Long fromMemberId, @Param("toMemberId") Long toMemberId);
}
