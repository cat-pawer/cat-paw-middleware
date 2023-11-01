package com.catpaw.catpawmiddleware.service.recruit;

import com.catpaw.catpawmiddleware.common.factory.dto.RecruitDtoFactory;
import com.catpaw.catpawmiddleware.domain.entity.Category;
import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.domain.entity.Tag;
import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import com.catpaw.catpawmiddleware.repository.recruit.RecruitRepository;
import com.catpaw.catpawmiddleware.service.category.CategoryService;
import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitSummaryDto;
import com.catpaw.catpawmiddleware.service.tag.TagService;
import com.catpaw.catpawmiddleware.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitService {

    private final RecruitRepository recruitRepository;
    private final TagService tagService;
    private final CategoryService categoryService;


    public CustomPageDto<RecruitSummaryDto> getPagedRecruitSummary(Pageable pageable) {
        RecruitSearchCond searchCond = new RecruitSearchCond();
        searchCond.setState(RecruitState.ACTIVE);

        Page<Recruit> pagedRecruitList = recruitRepository.findPagedRecruitList(searchCond, pageable);
        List<Long> idList = pagedRecruitList.stream().map(Recruit::getId).toList();

        Map<Long, List<Tag>> tagMapper =
                tagService.getTagMapByRecruitIdList(idList);

        Map<Long, List<Category>> categoryMapper =
                categoryService.getCategoryMapByIdListAndType(idList, TargetType.RECRUIT);

        List<RecruitSummaryDto> recruitSummaryDtoList = pagedRecruitList.stream()
                .map(recruit -> RecruitDtoFactory.toRecruitSummary(recruit, tagMapper, categoryMapper))
                .toList();

        return PageUtils.copyContents(pagedRecruitList, recruitSummaryDtoList);
    }

    public CustomPageDto<RecruitSummaryDto> getSlicedRecruitSummary(Pageable pageable) {
        RecruitSearchCond searchCond = new RecruitSearchCond();
        searchCond.setState(RecruitState.ACTIVE);

        Slice<Recruit> slicedRecruitList = recruitRepository.findSlicedRecruitList(searchCond, pageable);
        List<Long> idList = slicedRecruitList.stream().map(Recruit::getId).toList();

        Map<Long, List<Tag>> tagMapper =
                tagService.getTagMapByRecruitIdList(idList);

        Map<Long, List<Category>> categoryMapper =
                categoryService.getCategoryMapByIdListAndType(idList, TargetType.RECRUIT);

        List<RecruitSummaryDto> recruitSummaryDtoList = slicedRecruitList.stream()
                .map(recruit -> RecruitDtoFactory.toRecruitSummary(recruit, tagMapper, categoryMapper))
                .toList();

        return PageUtils.copyContents(slicedRecruitList, recruitSummaryDtoList);
    }
}
