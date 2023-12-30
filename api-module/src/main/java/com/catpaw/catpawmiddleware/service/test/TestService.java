package com.catpaw.catpawmiddleware.service.test;

import com.catpaw.catpawcore.domain.entity.*;
import com.catpaw.catpawcore.domain.eumns.*;
import com.catpaw.catpawcore.exception.custom.DataNotFoundException;
import com.catpaw.catpawmiddleware.repository.category.CategoryRepository;
import com.catpaw.catpawmiddleware.repository.comment.CommentRecruitRepository;
import com.catpaw.catpawmiddleware.repository.groups.GroupBoardRepository;
import com.catpaw.catpawmiddleware.repository.groups.GroupMemberRepository;
import com.catpaw.catpawmiddleware.repository.groups.GroupsRepository;
import com.catpaw.catpawmiddleware.repository.member.MemberRepository;
import com.catpaw.catpawmiddleware.repository.recruit.RecruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TestService {

    @Autowired
    RecruitRepository recruitRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CommentRecruitRepository commentRecruitRepository;

    @Autowired
    GroupsRepository groupsRepository;
    @Autowired
    GroupMemberRepository groupMemberRepository;
    @Autowired
    GroupBoardRepository groupBoardRepository;


    @Transactional
    public void dataInit() {
        List<Member> members = new ArrayList<>();
        List<Category> techList = new ArrayList<>();
        List<Category> hashList = new ArrayList<>();
        List<Recruit> recruitList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            members.add(createTestMember(i));
        }

        for (int i = 0; i < 10; i++) {
            createTechCategory(techList, i);
        }

        for (int i = 0; i < 10; i++) {
            createHashCategory(hashList, i);
        }

        for (int i = 0; i < 10; i++) {
            recruitList.add(createRecruit(techList, hashList, i));
        }

        for (int i = 0; i < 10; i++) {
            createRecruitComment(members, recruitList, i);
        }

        Member member1 = members.stream().filter(member -> member.getId().equals(1L)).findAny().orElseThrow(() -> {
            throw new DataNotFoundException();
        });

        Groups groups = new Groups();
        groups.setName("test group1");
        groups.setType(GroupType.PROJECT);
        groups.setDetail("detail");
        groups.setState(GroupState.ACTIVE);
        groups.setEndDate(LocalDate.now().plusDays(10));
        groups.addCreator(member1);

        GroupMember groupMember1 = new GroupMember();
        groupMember1.setAuth(Auth.MEMBER);
        groupMember1.setState(GroupMemberState.JOIN);
        groupMember1.addGroups(groups);
        groupMember1.addMember(member1);

        groups.setMemberList(List.of(groupMember1));

        groupsRepository.save(groups);
        groupMemberRepository.save(groupMember1);

        GroupBoard groupBoard1 = new GroupBoard();
        groupBoard1.setGroups(groups);
        groupBoard1.setTitle("hello1");
        groupBoard1.setContent("@2");
        groupBoardRepository.save(groupBoard1);

        GroupBoard groupBoard2 = new GroupBoard();
        groupBoard2.setGroups(groups);
        groupBoard2.setTitle("hello2");
        groupBoard2.setContent("@3");
        groupBoardRepository.save(groupBoard2);
    }

    private void createRecruitComment(List<Member> members, List<Recruit> recruitList, int i) {
        CommentRecruit commentRecruit = new CommentRecruit();
        commentRecruit.changeMember(members.get(i));
        commentRecruit.changeRecruit(recruitList.get(0));
        commentRecruit.setContent("text" + i);
        commentRecruitRepository.save(commentRecruit);
    }

    private Recruit createRecruit(List<Category> techList, List<Category> hashList, int i) {
        Recruit saveRecruit = makeRecruit(i);

        if (i % 3 == 0) {
            createCategoryMapper(techList.get(0), saveRecruit);
        }

        if (i % 5 == 0) {
            createCategoryMapper(techList.get(1), saveRecruit);
        }

        if (i % 7 == 0) {
            createCategoryMapper(techList.get(2), saveRecruit);
        }

        if (i % 2 == 0) {
            createCategoryMapper(hashList.get(0), saveRecruit);
        }

        if (i % 3 == 0) {
            createCategoryMapper(hashList.get(1), saveRecruit);
        }

        if (i % 5 == 0) {
            createCategoryMapper(hashList.get(2), saveRecruit);
        }

        return saveRecruit;
    }

    private Recruit makeRecruit(int i) {
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
        return recruitRepository.save(recruit);
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

    private void createCategoryMapper(Category category, Recruit saveRecruit) {
        CategoryMapper categoryMapper = new CategoryMapper();
        categoryMapper.addType(TargetType.RECRUIT);
        categoryMapper.addTargetId(saveRecruit.getId());
        categoryMapper.addCategory(category);
        categoryRepository.save(categoryMapper);
    }
}
