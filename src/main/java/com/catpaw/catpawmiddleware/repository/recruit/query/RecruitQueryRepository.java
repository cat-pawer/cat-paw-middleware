package com.catpaw.catpawmiddleware.repository.recruit.query;

import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import com.catpaw.catpawmiddleware.repository.condition.RecruitTopicCond;
import com.catpaw.catpawmiddleware.repository.dto.RecruitDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface RecruitQueryRepository {

    Optional<RecruitDetailDto> findRecruitDetailDto(Long recruitId);

    Page<Recruit> findPagedRecruitListWithCategory(RecruitSearchCond searchCond, Pageable pageable);

    Slice<Recruit> findSlicedRecruitListWithCategory(RecruitSearchCond searchCond, Pageable pageable);

    Page<Recruit> findPagedRecruitForTopic(RecruitTopicCond topicCond, Pageable pageable);

    Slice<Recruit> findSlicedRecruitForTopic(RecruitTopicCond topicCond, Pageable pageable);
}
