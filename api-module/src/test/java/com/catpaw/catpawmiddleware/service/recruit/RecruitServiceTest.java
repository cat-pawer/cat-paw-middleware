package com.catpaw.catpawmiddleware.service.recruit;

import com.catpaw.catpawcore.domain.entity.Recruit;
import com.catpaw.catpawcore.domain.eumns.RecruitState;
import com.catpaw.catpawcore.domain.eumns.TargetType;
import com.catpaw.catpawcore.exception.custom.DataNotFoundException;
import com.catpaw.catpawcore.exception.custom.ForbiddenException;
import com.catpaw.catpawmiddleware.controller.v1.request.enums.RecruitTopicRequest;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import com.catpaw.catpawmiddleware.repository.condition.RecruitTopicCond;
import com.catpaw.catpawmiddleware.repository.dto.RecruitDetailDto;
import com.catpaw.catpawmiddleware.repository.recruit.RecruitRepository;
import com.catpaw.catpawmiddleware.service.MockBaseTest;
import com.catpaw.catpawmiddleware.service.category.CategoryService;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitSearchDto;
import com.catpaw.catpawmiddleware.service.dto.recruit.RecruitTopicDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@Slf4j
class RecruitServiceTest extends MockBaseTest {


    @InjectMocks
    private RecruitService recruitService;

    @Mock
    private RecruitRepository recruitRepository;

    @Mock
    private CategoryService categoryService;

    @Test
    @DisplayName("getRecruitSummaryForSearch 페이지 조회")
    void getRecruitSummaryForSearchPage() {
        // given
        PageRequest pageable = PageRequest.of(0, 20, Sort.by(new Sort.Order(Sort.Direction.DESC, "created")));
        RecruitSearchDto searchDto = new RecruitSearchDto();
        given(recruitRepository.findPagedRecruitListWithCategory(any(RecruitSearchCond.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(new Recruit()), pageable, 0));
        given(categoryService.getMapByCategoryTypeList(anyList(), any(TargetType.class), anyList()))
                .willReturn(new HashMap<>());

        // when
        recruitService.getRecruitSummaryForSearch(searchDto, pageable, true);

        // then
        verify(categoryService, times(1)).getMapByCategoryTypeList(anyList(), any(TargetType.class), anyList());
        verify(recruitRepository, times(1)).findPagedRecruitListWithCategory(any(RecruitSearchCond.class), any(Pageable.class));
        verify(recruitRepository, times(0)).findSlicedRecruitListWithCategory(any(RecruitSearchCond.class), any(Pageable.class));
    }

    @Test
    @DisplayName("getRecruitSummaryForSearch 스크롤 조회")
    void getRecruitSummaryForSearchSlice() {
        // given
        PageRequest pageable = PageRequest.of(0, 20, Sort.by(new Sort.Order(Sort.Direction.DESC, "created")));
        RecruitSearchDto searchDto = new RecruitSearchDto();
        given(recruitRepository.findSlicedRecruitListWithCategory(any(RecruitSearchCond.class), any(Pageable.class)))
                .willReturn(new SliceImpl<>(List.of(new Recruit()), pageable, true));
        given(categoryService.getMapByCategoryTypeList(anyList(), any(TargetType.class), anyList()))
                .willReturn(new HashMap<>());

        // when
        recruitService.getRecruitSummaryForSearch(searchDto, pageable, false);

        // then
        verify(categoryService, times(1)).getMapByCategoryTypeList(anyList(), any(TargetType.class), anyList());
        verify(recruitRepository, times(1)).findSlicedRecruitListWithCategory(any(RecruitSearchCond.class), any(Pageable.class));
        verify(recruitRepository, times(0)).findPagedRecruitListWithCategory(any(RecruitSearchCond.class), any(Pageable.class));
    }

    @Test
    @DisplayName("getRecruitSummaryForTopic 페이지 조회")
    void getRecruitSummaryForTopicPage() {
        // given
        PageRequest pageable = PageRequest.of(0, 20, Sort.by(new Sort.Order(Sort.Direction.DESC, "created")));
        given(recruitRepository.findPagedRecruitForTopic(any(RecruitTopicCond.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(), pageable, 0));
        given(categoryService.getMapByCategoryTypeList(anyList(), any(TargetType.class), anyList()))
                .willReturn(new HashMap<>());
        RecruitTopicDto topicDto = new RecruitTopicDto();
        topicDto.setTopic(RecruitTopicRequest.DEADLINE.toEnum());

        // when
        recruitService.getRecruitSummaryForTopic(topicDto, pageable, true);

        // then
        verify(categoryService, times(1)).getMapByCategoryTypeList(anyList(), any(TargetType.class), anyList());
        verify(recruitRepository, times(1)).findPagedRecruitForTopic(any(RecruitTopicCond.class), any(Pageable.class));
        verify(recruitRepository, times(0)).findSlicedRecruitForTopic(any(RecruitTopicCond.class), any(Pageable.class));
    }

    @Test
    @DisplayName("getRecruitSummaryForTopic 스크롤 조회")
    void getRecruitSummaryForTopicSlice() {
        // given
        PageRequest pageable = PageRequest.of(0, 20, Sort.by(new Sort.Order(Sort.Direction.DESC, "created")));
        given(recruitRepository.findPagedRecruitForTopic(any(RecruitTopicCond.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(), pageable, 0));
        given(categoryService.getMapByCategoryTypeList(anyList(), any(TargetType.class), anyList()))
                .willReturn(new HashMap<>());
        RecruitTopicDto topicDto = new RecruitTopicDto();
        topicDto.setTopic(RecruitTopicRequest.DEADLINE.toEnum());

        // when
        recruitService.getRecruitSummaryForTopic(topicDto, pageable, true);

        // then
        verify(categoryService, times(1)).getMapByCategoryTypeList(anyList(), any(TargetType.class), anyList());
        verify(recruitRepository, times(1)).findPagedRecruitForTopic(any(RecruitTopicCond.class), any(Pageable.class));
        verify(recruitRepository, times(0)).findSlicedRecruitForTopic(any(RecruitTopicCond.class), any(Pageable.class));
    }

    @Test
    @DisplayName("getRecruitSummaryForTopic 토픽 null 검증")
    void getRecruitSummaryForTopicNullValid() {
        // given
        PageRequest pageable = PageRequest.of(0, 20, Sort.by(new Sort.Order(Sort.Direction.DESC, "created")));
        given(recruitRepository.findPagedRecruitForTopic(any(RecruitTopicCond.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(), pageable, 0));
        given(categoryService.getMapByCategoryTypeList(anyList(), any(TargetType.class), anyList()))
                .willReturn(new HashMap<>());
        RecruitTopicDto topicDto = new RecruitTopicDto();

        // when
        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> recruitService.getRecruitSummaryForTopic(topicDto, pageable, true));
    }

    @Test
    @DisplayName("getRecruitDetail 모집글 없을 경우")
    void getRecruitDetailNotFound() {
        // given
        Long memberId = 1L;
        Long recruitId = 1L;
        given(recruitRepository.findRecruitDetailDto(anyLong()))
                .willReturn(Optional.empty());

        // when
        // then
        Assertions.assertThrows(DataNotFoundException.class, () -> recruitService.getAccessibleRecruitDetail(recruitId, memberId));
    }

    @Test
    @DisplayName("getRecruitDetail 본인외의 숨긴 글 접근")
    void getRecruitDetailForbidden() {
        // given
        Long memberId = 1L;
        Long recruitId = 1L;
        Long creatorId = 2L;
        RecruitDetailDto recruitDetailDto = new RecruitDetailDto();
        recruitDetailDto.setId(recruitId);
        recruitDetailDto.setCreatedBy(creatorId);
        recruitDetailDto.setState(RecruitState.DISABLE);

        given(recruitRepository.findRecruitDetailDto(anyLong()))
                .willReturn(Optional.of(recruitDetailDto));

        // when
        // then
        Assertions.assertThrows(ForbiddenException.class, () -> recruitService.getAccessibleRecruitDetail(recruitId, memberId));
    }

    @Test
    @DisplayName("getRecruitDetail 익명 사용자 숨긴 글 접근")
    void getRecruitDetailAnonymousForbidden() {
        // given
        Long memberId = null;
        Long recruitId = 1L;
        Long creatorId = 2L;
        RecruitDetailDto recruitDetailDto = new RecruitDetailDto();
        recruitDetailDto.setId(recruitId);
        recruitDetailDto.setCreatedBy(creatorId);
        recruitDetailDto.setState(RecruitState.DISABLE);

        given(recruitRepository.findRecruitDetailDto(anyLong()))
                .willReturn(Optional.of(recruitDetailDto));

        // when
        // then
        Assertions.assertThrows(ForbiddenException.class, () -> recruitService.getAccessibleRecruitDetail(recruitId, memberId));
    }

    @Test
    @DisplayName("getRecruitDetail 본인의 숨긴 글 접근")
    void getRecruitDetailAcceptMember() {
        // given
        Long memberId = 1L;
        Long recruitId = 1L;
        Long creatorId = 1L;
        RecruitDetailDto recruitDetailDto = new RecruitDetailDto();
        recruitDetailDto.setId(recruitId);
        recruitDetailDto.setCreatedBy(creatorId);
        recruitDetailDto.setState(RecruitState.DISABLE);

        given(recruitRepository.findRecruitDetailDto(anyLong()))
                .willReturn(Optional.of(recruitDetailDto));

        // when
        RecruitDetailDto recruitDetail = recruitService.getAccessibleRecruitDetail(recruitId, memberId);

        // then
        assertThat(recruitDetail.getId()).isEqualTo(recruitId);
        assertThat(recruitDetail.getCreatedBy()).isEqualTo(creatorId);
    }
}