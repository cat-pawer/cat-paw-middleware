package com.catpaw.catpawmiddleware.service.recruit;

import com.catpaw.catpawmiddleware.common.factory.dto.RecruitDtoFactory;
import com.catpaw.catpawmiddleware.controller.request.enums.RecruitTopicRequest;
import com.catpaw.catpawmiddleware.domain.entity.CategoryMapper;
import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.domain.eumns.CategoryType;
import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import com.catpaw.catpawmiddleware.exception.custom.DataNotFoundException;
import com.catpaw.catpawmiddleware.exception.custom.ForbiddenException;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import com.catpaw.catpawmiddleware.repository.condition.RecruitTopicCond;
import com.catpaw.catpawmiddleware.repository.dto.RecruitDetailDto;
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
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitService {

    private final RecruitRepository recruitRepository;
    private final CategoryService categoryService;


    public RecruitDetailDto getRecruitDetail(Long recruitId, Long memberId) {
        Optional<RecruitDetailDto> findRecruitDetailDto = recruitRepository.findRecruitDetailDto(recruitId);
        RecruitDetailDto recruitDetailDto = findRecruitDetailDto.orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 모집글입니다.");
        });

        if (RecruitState.DISABLE.equals(recruitDetailDto.getState())) {
            if (!recruitDetailDto.getCreatedBy().equals(memberId)) {
                throw new ForbiddenException("접근할 수 없는 모집글입니다.");
            }
        }

        return recruitDetailDto;
    }

    public CustomPageDto<RecruitSummaryDto> getRecruitSummaryForSearch(RecruitSearchDto searchDto, Pageable pageable, boolean isPage) {
        RecruitSearchCond searchCond = new RecruitSearchCond();
        searchCond.setSearchValue(searchDto.getSearchValue());
        searchCond.setRecruitType(searchDto.getRecruitType());
        searchCond.setOnlineType(searchDto.getOnlineType());
        searchCond.setCategoryIdList(searchDto.getCategoryIdList());
        searchCond.setRecruitPeriod(searchDto.getRecruitPeriod() == null ? LocalDate.now() : searchDto.getRecruitPeriod());
        searchCond.setState(searchDto.getState() == null ? RecruitState.ACTIVE : searchDto.getState());

        if (isPage) {
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

    public CustomPageDto<RecruitSummaryDto> getRecruitSummaryForTopic(RecruitTopicDto topicDto, Pageable pageable, boolean isPage) {
        Assert.notNull(topicDto, "topicDto는 null일 수 없습니다.");
        Assert.notNull(topicDto.getTopic(), "topic은 null일 수 없습니다.");

        RecruitTopicCond topicCond = new RecruitTopicCond();
        topicCond.setTopic(topicDto.getTopic());
        topicCond.setRecruitPeriod(topicDto.getRecruitPeriod() == null ? LocalDate.now() : topicDto.getRecruitPeriod());
        topicCond.setLimitPeriod(LocalDate.now().plusDays(7).atTime(LocalTime.MAX));
        topicCond.setState(topicDto.getState() == null ? RecruitState.ACTIVE : topicDto.getState());

        if (isPage) {
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
