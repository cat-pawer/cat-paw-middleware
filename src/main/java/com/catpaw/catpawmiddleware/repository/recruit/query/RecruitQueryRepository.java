package com.catpaw.catpawmiddleware.repository.recruit.query;

import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import com.catpaw.catpawmiddleware.repository.condition.RecruitTopicCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface RecruitQueryRepository {

    Page<Recruit> findPagedRecruitList(RecruitSearchCond searchCond, Pageable pageable);

    Slice<Recruit> findSlicedRecruitList(RecruitSearchCond searchCond, Pageable pageable);

    Page<Recruit> findPagedRecruitListForTag(RecruitSearchCond searchCond, Pageable pageable);

    Slice<Recruit> findSlicedRecruitListForTag(RecruitSearchCond searchCond, Pageable pageable);

    Page<Recruit> findPagedRecruitListForCategory(RecruitSearchCond searchCond, Pageable pageable);

    Slice<Recruit> findSlicedRecruitListForCategory(RecruitSearchCond searchCond, Pageable pageable);

    Page<Recruit> findPagedRecruitForTopic(RecruitTopicCond searchCond, Pageable pageable);

    Slice<Recruit> findSlicedRecruitForTopic(RecruitTopicCond searchCond, Pageable pageable);

}
