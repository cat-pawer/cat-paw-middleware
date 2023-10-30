package com.catpaw.catpawmiddleware.repository.friend;

import com.catpaw.catpawmiddleware.domain.entity.Friend;
import com.catpaw.catpawmiddleware.repository.friend.query.FriendQueryRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Long>, FriendCustomRepository, FriendQueryRepository {
}
