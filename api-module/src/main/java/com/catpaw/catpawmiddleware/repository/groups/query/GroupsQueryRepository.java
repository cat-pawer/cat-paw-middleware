package com.catpaw.catpawmiddleware.repository.groups.query;

import com.catpaw.catpawcore.domain.entity.Groups;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface GroupsQueryRepository {

    Slice<Groups> findSlicedMyGroups(long memberId, Pageable pageable);

    Slice<Groups> findSlicedOtherGroups(long memberId, Pageable pageable);
}
