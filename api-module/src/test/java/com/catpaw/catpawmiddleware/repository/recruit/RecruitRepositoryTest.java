package com.catpaw.catpawmiddleware.repository.recruit;

import com.catpaw.catpawmiddleware.controller.v1.request.enums.RecruitTopicRequest;
import com.catpaw.catpawcore.domain.entity.Category;
import com.catpaw.catpawcore.domain.entity.CategoryMapper;
import com.catpaw.catpawcore.domain.entity.Member;
import com.catpaw.catpawcore.domain.entity.Recruit;
import com.catpaw.catpawcore.domain.eumns.*;
import com.catpaw.catpawmiddleware.repository.DataBaseTest;
import com.catpaw.catpawmiddleware.repository.category.CategoryRepository;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import com.catpaw.catpawmiddleware.repository.condition.RecruitTopicCond;
import com.catpaw.catpawmiddleware.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class RecruitRepositoryTest extends DataBaseTest {

    @Autowired
    RecruitRepository recruitRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MemberRepository memberRepository;

    private Member testMember;

    @BeforeAll
    void beforeAll() {
        List<Member> members = new ArrayList<>();
        List<Category> techList = new ArrayList<>();
        List<Category> hashList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            members.add(createTestMember(i));
        }
        this.testMember = members.get(0);

        for (int i = 0; i < 10; i++) {
            createTechCategory(techList, i);
        }

        for (int i = 0; i < 30; i++) {
            createHashCategory(hashList, i);
        }

        for (int i = 0; i < 30; i++) {
            Recruit saveRecruit = createRecruit(i);

            List<CategoryMapper> categoryMappers = new ArrayList<>();

            if (i % 3 == 0) {
                CategoryMapper categoryMapper = new CategoryMapper();
                categoryMapper.addType(TargetType.RECRUIT);
                categoryMapper.addTargetId(saveRecruit.getId());
                categoryMapper.addCategory(techList.get(0));
                categoryMappers.add(categoryMapper);
            };

            if (i % 5 == 0) {
                CategoryMapper categoryMapper = new CategoryMapper();
                categoryMapper.addType(TargetType.RECRUIT);
                categoryMapper.addTargetId(saveRecruit.getId());
                categoryMapper.addCategory(techList.get(1));
                categoryMappers.add(categoryMapper);
            }

            if (i % 7 == 0) {
                CategoryMapper categoryMapper = new CategoryMapper();
                categoryMapper.addType(TargetType.RECRUIT);
                categoryMapper.addTargetId(saveRecruit.getId());
                categoryMapper.addCategory(techList.get(2));
                categoryMappers.add(categoryMapper);
            }

            if (i % 99 == 0) {
                CategoryMapper categoryMapper = new CategoryMapper();
                categoryMapper.addType(TargetType.RECRUIT);
                categoryMapper.addTargetId(saveRecruit.getId());
                categoryMapper.addCategory(techList.get(3));
                categoryMappers.add(categoryMapper);
            }

            if (i % 2 == 0) {
                CategoryMapper categoryMapper = new CategoryMapper();
                categoryMapper.addType(TargetType.RECRUIT);
                categoryMapper.addTargetId(saveRecruit.getId());
                categoryMapper.addCategory(hashList.get(0));
                categoryMappers.add(categoryMapper);
            }

            if (i % 3 == 0) {
                CategoryMapper categoryMapper = new CategoryMapper();
                categoryMapper.addType(TargetType.RECRUIT);
                categoryMapper.addTargetId(saveRecruit.getId());
                categoryMapper.addCategory(hashList.get(1));
                categoryMappers.add(categoryMapper);
            }

            if (i % 5 == 0) {
                CategoryMapper categoryMapper = new CategoryMapper();
                categoryMapper.addType(TargetType.RECRUIT);
                categoryMapper.addTargetId(saveRecruit.getId());
                categoryMapper.addCategory(hashList.get(2));
                categoryMappers.add(categoryMapper);
            }

            if (i % 99 == 0) {
                CategoryMapper categoryMapper = new CategoryMapper();
                categoryMapper.addType(TargetType.RECRUIT);
                categoryMapper.addTargetId(saveRecruit.getId());
                categoryMapper.addCategory(hashList.get(3));
                categoryMappers.add(categoryMapper);
            }

            categoryMappers.forEach(cm -> categoryRepository.save(cm));
        }
    }

    private Recruit createRecruit(int i) {
        Recruit recruit = new Recruit();
        recruit.setTitle("title" + i);
        recruit.setPeopleNumber(3);
        recruit.setExpectDuration(1);
        recruit.setState(RecruitState.ACTIVE);
        recruit.setOnlineType(OnlineType.ONLINE);
        recruit.setGroupType(GroupType.PROJECT);
        recruit.setRecruitPeriod(LocalDate.now().plusDays(i));
        recruit.setViewCount(0);
        recruit.setContent("asdasd");
        recruit.setContact("0102323");
        recruit.setCommentCount(0);
        Recruit saveRecruit = recruitRepository.save(recruit);
        return saveRecruit;
    }

    private void createHashCategory(List<Category> hashList, int i) {
        Category category = new Category();
        category.setName("tag" + i);
        category.setValue(null);
        category.setType(CategoryType.HASH);
        hashList.add(categoryRepository.save(category));
    }

    private void createTechCategory(List<Category> techList, int i) {
        Category category = new Category();
        category.setName("java" + i);
        category.setValue("img");
        category.setType(CategoryType.TECH_STACK);
        techList.add(categoryRepository.save(category));
    }

    private Member createTestMember(int i) {
        Member member = new Member();
        member.setName("hello" + i);
        member.setAuth(Auth.MEMBER);
        return memberRepository.save(member);
    }

    @Test
    void getPagedRecruitSummaryForSearch() {
        //given
        PageRequest pageable = PageRequest.of(0, 20, Sort.by(new Sort.Order(Sort.Direction.DESC, "created")));

        RecruitSearchCond cond = new RecruitSearchCond();
        cond.setSearchValue("hello");
        cond.setState(RecruitState.ACTIVE);
        cond.setRecruitPeriod(LocalDate.now());
        cond.setCategoryIdList(List.of(1L, 2L));

        //when
        Page<Recruit> pagedRecruitListWithCategory = recruitRepository.findPagedRecruitListWithCategory(cond, pageable);
        //then

        Assertions.assertThat(pagedRecruitListWithCategory.getSize()).isEqualTo(20);

    }

    @Test
    void getSlicedRecruitSummaryForSearch() {
        PageRequest pageable = PageRequest.of(0, 20, Sort.by(new Sort.Order(Sort.Direction.DESC, "created")));

        RecruitSearchCond cond = new RecruitSearchCond();
        cond.setSearchValue("hello");
        cond.setState(RecruitState.ACTIVE);
        cond.setRecruitPeriod(LocalDate.now());

        //when
        Slice<Recruit> pagedRecruitListWithCategory = recruitRepository.findSlicedRecruitListWithCategory(cond, pageable);
        //then

        Assertions.assertThat(pagedRecruitListWithCategory.getSize()).isEqualTo(20);
    }

    @Test
    void getPagedRecruitSummaryForTopic() {

        PageRequest pageable = PageRequest.of(0, 20, Sort.by(new Sort.Order(Sort.Direction.DESC, "created")));

        RecruitTopicCond topicCond = new RecruitTopicCond();
        topicCond.setTopic(RecruitTopicRequest.DEADLINE.toEnum());
        topicCond.setState(RecruitState.ACTIVE);
        topicCond.setRecruitPeriod(LocalDate.now());
        topicCond.setLimitPeriod(LocalDate.now().atTime(LocalTime.MAX));

        //when
        Page<Recruit> pagedRecruitListWithCategory = recruitRepository.findPagedRecruitForTopic(topicCond, pageable);
        //then

        Assertions.assertThat(pagedRecruitListWithCategory.getSize()).isEqualTo(20);
    }

    @Test
    void getSlicedRecruitSummaryForTopic() {

        PageRequest pageable = PageRequest.of(0, 20, Sort.by(new Sort.Order(Sort.Direction.DESC, "created")));

        RecruitTopicCond topicCond = new RecruitTopicCond();
        topicCond.setTopic(RecruitTopicRequest.DEADLINE.toEnum());
        topicCond.setState(RecruitState.ACTIVE);
        topicCond.setRecruitPeriod(LocalDate.now());
        topicCond.setLimitPeriod(LocalDate.now().atTime(LocalTime.MAX));

        //when
        Slice<Recruit> pagedRecruitListWithCategory = recruitRepository.findPagedRecruitForTopic(topicCond, pageable);
        //then

        Assertions.assertThat(pagedRecruitListWithCategory.getSize()).isEqualTo(20);
    }
}