package com.catpaw.catpawmiddleware.repository.recruit.query;

import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface RecruitQueryRepository {

    Page<Recruit> findPagedRecruitList(RecruitSearchCond cond, Pageable pageable);

    Slice<Recruit> findSlicedRecruitList(RecruitSearchCond cond, Pageable pageable);

}
