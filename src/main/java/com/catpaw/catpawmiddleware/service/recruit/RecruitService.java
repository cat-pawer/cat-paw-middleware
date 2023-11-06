package com.catpaw.catpawmiddleware.service.recruit;

import com.catpaw.catpawmiddleware.common.factory.dto.RecruitDtoFactory;
import com.catpaw.catpawmiddleware.controller.request.search.SearchForm;
import com.catpaw.catpawmiddleware.domain.entity.CategoryMapper;
import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.domain.eumns.CategoryType;
import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import com.catpaw.catpawmiddleware.repository.category.CategoryMapperRepository;
import com.catpaw.catpawmiddleware.repository.category.CategoryRepository;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import com.catpaw.catpawmiddleware.repository.condition.RecruitTopicCond;
import com.catpaw.catpawmiddleware.repository.recruit.RecruitRepository;
import com.catpaw.catpawmiddleware.service.category.CategoryService;
import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitSearchDto;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitSummaryDto;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitTopicDto;
import com.catpaw.catpawmiddleware.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitService {

    private final RecruitRepository recruitRepository;
    private final CategoryService categoryService;


    public CustomPageDto<RecruitSummaryDto> getRecruitSummaryForSearch(RecruitSearchDto searchDto, Pageable pageable) {
        RecruitSearchCond searchCond = new RecruitSearchCond();
        searchCond.setSearchValue(searchCond.getSearchValue());
        searchCond.setRecruitType(searchCond.getRecruitType());
        searchCond.setOnlineType(searchCond.getOnlineType());
        searchCond.setCategoryIdList(searchDto.getCategoryIdList());
        searchCond.setRecruitPeriod(searchDto.getRecruitPeriod() == null ? LocalDate.now() : searchDto.getRecruitPeriod());
        searchCond.setState(searchDto.getState() == null ? RecruitState.ACTIVE : searchDto.getState());

        if (pageable.isPaged()) {
            Page<Recruit> pagedRecruitList = recruitRepository.findPagedRecruitListWithCategory(searchCond, pageable);
            Map<Long, List<CategoryMapper>> categoryMapperMap =
                    this.getCategoryMapByIdList(
                            pagedRecruitList.getContent().stream().map(Recruit::getId).toList(),
                            List.of(CategoryType.TECH_STACK, CategoryType.HASH));

            return PageUtils.createCustomPageDto(
                    pagedRecruitList.map(recruit -> RecruitDtoFactory.toRecruitSummary(recruit, categoryMapperMap)));
        }
        else {
            Slice<Recruit> slicedRecruitList = recruitRepository.findSlicedRecruitListWithCategory(searchCond, pageable);
            Map<Long, List<CategoryMapper>> categoryMapperMap =
                    this.getCategoryMapByIdList(
                            slicedRecruitList.getContent().stream().map(Recruit::getId).toList(),
                            List.of(CategoryType.TECH_STACK, CategoryType.HASH));

            return PageUtils.createCustomPageDto(
                    slicedRecruitList.map(recruit -> RecruitDtoFactory.toRecruitSummary(recruit, categoryMapperMap)));
        }
    }

    public CustomPageDto<RecruitSummaryDto> getRecruitSummaryForTopic(RecruitTopicDto topicDto, Pageable pageable) {
        List<String> supportTopicList = List.of(SearchForm.DEADLINE.getValue(), SearchForm.ISNEW.getValue());
        if (!supportTopicList.contains(topicDto.getTopic())) {
            throw new IllegalArgumentException("잘못된 검색 조건입니다.");
        }

        RecruitTopicCond topicCond = new RecruitTopicCond();
        topicCond.setRecruitPeriod(topicDto.getRecruitPeriod() == null ? LocalDate.now() : topicDto.getRecruitPeriod());
        topicCond.setLimitPeriod(LocalDate.now().plusDays(7).atTime(LocalTime.MAX));
        topicCond.setState(topicDto.getState() == null ? RecruitState.ACTIVE : topicDto.getState());

        if (pageable.isPaged()) {
            Page<Recruit> pagedRecruitList = recruitRepository.findPagedRecruitForTopic(topicCond, pageable);
            Map<Long, List<CategoryMapper>> categoryMapperMap =
                    this.getCategoryMapByIdList(
                            pagedRecruitList.getContent().stream().map(Recruit::getId).toList(),
                            List.of(CategoryType.HASH));

            return PageUtils.createCustomPageDto(
                    pagedRecruitList.map(recruit -> RecruitDtoFactory.toRecruitSummary(recruit, categoryMapperMap)));
        }
        else {
            Slice<Recruit> slicedRecruitList = recruitRepository.findSlicedRecruitForTopic(topicCond, pageable);
            Map<Long, List<CategoryMapper>> categoryMapperMap =
                    this.getCategoryMapByIdList(
                            slicedRecruitList.getContent().stream().map(Recruit::getId).toList(),
                            List.of(CategoryType.HASH));

            return PageUtils.createCustomPageDto(
                    slicedRecruitList.map(recruit -> RecruitDtoFactory.toRecruitSummary(recruit, categoryMapperMap)));
        }
    }

    private Map<Long, List<CategoryMapper>> getCategoryMapByIdList(List<Long> idList, List<CategoryType> categoryTypeList) {
        return categoryService.getMapByCategoryTypeList(idList, TargetType.RECRUIT, categoryTypeList);
    }
}
