package com.catpaw.catpawmiddleware.repository.recruit.query;

import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import com.catpaw.catpawmiddleware.repository.condition.RecruitTopicCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface RecruitQueryRepository {

    Page<Recruit> findPagedRecruitListWithCategory(RecruitSearchCond searchCond, Pageable pageable);

    Slice<Recruit> findSlicedRecruitListWithCategory(RecruitSearchCond searchCond, Pageable pageable);

    Page<Recruit> findPagedRecruitForTopic(RecruitTopicCond topicCond, Pageable pageable);

    Slice<Recruit> findSlicedRecruitForTopic(RecruitTopicCond topicCond, Pageable pageable);
}
