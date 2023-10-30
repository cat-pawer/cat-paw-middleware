package com.catpaw.catpawmiddleware.repository.friend.query;

import com.catpaw.catpawmiddleware.domain.entity.Friend;
import com.catpaw.catpawmiddleware.repository.condition.FriendSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FriendQueryRepository {

    Page<Friend> findPagedFriendList(FriendSearchCond searchCond, Pageable pageable);

    Slice<Friend> findSlicedFriendList(FriendSearchCond searchCond, Pageable pageable);

    Page<Friend> findPagedMyRequestFriendList(FriendSearchCond searchCond, Pageable pageable);

    Slice<Friend> findSlicedMyRequestFriendList(FriendSearchCond searchCond, Pageable pageable);

    Page<Friend> findPagedOtherRequestFriendList(FriendSearchCond searchCond, Pageable pageable);

    Slice<Friend> findSlicedOtherRequestFriendList(FriendSearchCond searchCond, Pageable pageable);
}
