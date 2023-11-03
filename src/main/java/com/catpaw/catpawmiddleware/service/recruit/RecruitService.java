package com.catpaw.catpawmiddleware.service.recruit;

import com.catpaw.catpawmiddleware.common.factory.dto.RecruitDtoFactory;
import com.catpaw.catpawmiddleware.controller.request.search.SearchForm;
import com.catpaw.catpawmiddleware.domain.entity.Category;
import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.domain.entity.Tag;
import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import com.catpaw.catpawmiddleware.repository.condition.RecruitTopicCond;
import com.catpaw.catpawmiddleware.repository.recruit.RecruitRepository;
import com.catpaw.catpawmiddleware.service.category.CategoryService;
import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitSearchDto;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitSummaryDto;
import com.catpaw.catpawmiddleware.service.tag.TagService;
import com.catpaw.catpawmiddleware.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

        return PageUtils.createCustomPageDto(pagedRecruitList
                .map(recruit -> RecruitDtoFactory.toRecruitSummary(recruit, tagMapper, categoryMapper)));
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

        return PageUtils.createCustomPageDto(slicedRecruitList
                .map(recruit -> RecruitDtoFactory.toRecruitSummary(recruit, tagMapper, categoryMapper)));
    }

    public CustomPageDto<RecruitSummaryDto> getPagedRecruitSummaryForSearch(RecruitSearchDto searchDto, Pageable pageable) {
        if (searchDto.getTagId() != null && searchDto.getCategoryId() != null) {
            throw new IllegalArgumentException("카테고리 혹은 태그 검색은 하나만 선택 가능합니다.");
        }

        RecruitSearchCond searchCond = new RecruitSearchCond();
        searchCond.setRecruitType(searchCond.getRecruitType());
        searchCond.setCategoryId(searchCond.getCategoryId());
        searchCond.setTagId(searchCond.getTagId());
        searchCond.setOnlineType(searchCond.getOnlineType());
        searchCond.setState(searchDto.getState() == null ? RecruitState.ACTIVE : searchDto.getState());

        Page<Recruit> paged;
        if (searchCond.getTagId() != null) {
            paged = recruitRepository.findPagedRecruitListForTag(searchCond, pageable);
        }
        else if (searchCond.getCategoryId() != null) {
            searchCond.setTargetType(TargetType.RECRUIT);
            paged = recruitRepository.findPagedRecruitListForCategory(searchCond, pageable);
        }
        else {
            paged = recruitRepository.findPagedRecruitList(searchCond, pageable);
        }

        List<Long> idList = paged.stream().map(Recruit::getId).toList();
        Map<Long, List<Tag>> tagMapper =
                tagService.getTagMapByRecruitIdList(idList);

        return PageUtils.createCustomPageDto(paged.map(recruit -> RecruitDtoFactory.toRecruitSummary(recruit, tagMapper)));
    }

    public CustomPageDto<RecruitSummaryDto> getSlicedRecruitSummaryForSearch(RecruitSearchDto searchDto, Pageable pageable) {
        if (searchDto.getTagId() != null && searchDto.getCategoryId() != null) {
            throw new IllegalArgumentException("카테고리 혹은 태그 검색은 하나만 선택 가능합니다.");
        }

        RecruitSearchCond searchCond = new RecruitSearchCond();
        searchCond.setRecruitType(searchCond.getRecruitType());
        searchCond.setCategoryId(searchCond.getCategoryId());
        searchCond.setTagId(searchCond.getTagId());
        searchCond.setOnlineType(searchCond.getOnlineType());
        searchCond.setState(searchDto.getState() == null ? RecruitState.ACTIVE : searchDto.getState());

        Slice<Recruit> sliced;
        if (searchCond.getTagId() != null) {
            sliced = recruitRepository.findSlicedRecruitListForTag(searchCond, pageable);
        }
        else if (searchCond.getCategoryId() != null) {
            searchCond.setTargetType(TargetType.RECRUIT);
            sliced = recruitRepository.findSlicedRecruitListForCategory(searchCond, pageable);
        }
        else {
            sliced = recruitRepository.findSlicedRecruitList(searchCond, pageable);
        }

        List<Long> idList = sliced.stream().map(Recruit::getId).toList();
        Map<Long, List<Tag>> tagMapper =
                tagService.getTagMapByRecruitIdList(idList);

        return PageUtils.createCustomPageDto(sliced.map(recruit -> RecruitDtoFactory.toRecruitSummary(recruit, tagMapper)));
    }

    public CustomPageDto<RecruitSummaryDto> getPagedRecruitSummaryForTopic(String topic, Pageable pageable) {

        if (!SearchForm.DEADLINE.getValue().equals(topic) && !SearchForm.ISNEW.getValue().equals(topic)) {
            throw new IllegalArgumentException("잘못된 검색 조건입니다.");
        }

        else {
            RecruitTopicCond topicCond = new RecruitTopicCond();
            topicCond.setTopic(topic);
            topicCond.setState(RecruitState.ACTIVE);
            if (SearchForm.DEADLINE.getValue().equals(topic)) topicCond.setRecruitPeriod(LocalDate.now());

            Page<Recruit> pagedRecruitList = recruitRepository.findPagedRecruitForTopic(topicCond, pageable);
            List<Long> idList = pagedRecruitList.stream().map(Recruit::getId).toList();
            Map<Long, List<Tag>> tagMapper = tagService.getTagMapByRecruitIdList(idList);

            return PageUtils.createCustomPageDto(pagedRecruitList.map(recruit -> RecruitDtoFactory.toRecruitSummary(recruit, tagMapper)));
        }
    }

    public CustomPageDto<RecruitSummaryDto> getSlicedRecruitSummaryForTopic(String topic, Pageable pageable) {

        if (!SearchForm.DEADLINE.getValue().equals(topic) && !SearchForm.ISNEW.getValue().equals(topic)) {
            throw new IllegalArgumentException("잘못된 검색 조건입니다.");
        }

        else {
            RecruitTopicCond topicCond = new RecruitTopicCond();
            topicCond.setTopic(topic);
            topicCond.setState(RecruitState.ACTIVE);
            if (SearchForm.DEADLINE.getValue().equals(topic)) topicCond.setRecruitPeriod(LocalDate.now());

            Slice<Recruit> slicedRecruitList = recruitRepository.findSlicedRecruitForTopic(topicCond, pageable);
            List<Long> idList = slicedRecruitList.stream().map(Recruit::getId).toList();
            Map<Long, List<Tag>> tagMapper = tagService.getTagMapByRecruitIdList(idList);

            return PageUtils.createCustomPageDto(slicedRecruitList.map(recruit -> RecruitDtoFactory.toRecruitSummary(recruit, tagMapper)));
        }
    }
}
