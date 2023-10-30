package com.catpaw.catpawmiddleware.repository.friend.query;

import com.catpaw.catpawmiddleware.domain.entity.Friend;
import com.catpaw.catpawmiddleware.repository.condition.FriendSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface FriendQueryRepository {

    Page<Friend> findFriendSummaryPage(FriendSearchCond searchCond, Pageable pageable);

    Slice<Friend> findFriendSummarySlice(FriendSearchCond searchCond, Pageable pageable);

    Page<Friend> findMyRequestFriendSummaryPage(FriendSearchCond searchCond, Pageable pageable);

    Slice<Friend> findMyRequestFriendSummarySlice(FriendSearchCond searchCond, Pageable pageable);

    Page<Friend> findOtherRequestFriendSummaryPage(FriendSearchCond searchCond, Pageable pageable);

    Slice<Friend> findOtherRequestFriendSummarySlice(FriendSearchCond searchCond, Pageable pageable);
}
