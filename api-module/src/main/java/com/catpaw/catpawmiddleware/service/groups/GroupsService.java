package com.catpaw.catpawmiddleware.service.groups;

import com.catpaw.catpawcore.common.factory.dto.CustomPageDtoFactory;
import com.catpaw.catpawcore.common.factory.dto.GroupDtoFactory;
import com.catpaw.catpawcore.domain.dto.service.CustomPageDto;
import com.catpaw.catpawcore.domain.dto.service.group.GroupBoardSummaryDto;
import com.catpaw.catpawcore.domain.dto.service.group.GroupsDetailDto;
import com.catpaw.catpawcore.domain.dto.service.group.GroupsSummaryDto;
import com.catpaw.catpawcore.domain.entity.GroupBoard;
import com.catpaw.catpawcore.domain.entity.Groups;
import com.catpaw.catpawcore.domain.object.group.GroupBoardContents;
import com.catpaw.catpawcore.exception.custom.DataNotFoundException;
import com.catpaw.catpawcore.exception.custom.ForbiddenException;
import com.catpaw.catpawcore.utils.LogUtils;
import com.catpaw.catpawmiddleware.repository.groups.GroupBoardRepository;
import com.catpaw.catpawmiddleware.repository.groups.GroupsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GroupsService {

    private final GroupsRepository groupsRepository;
    private final GroupBoardRepository groupBoardRepository;

    public CustomPageDto<GroupsSummaryDto> getMyGroupsSummary(long memberId, Pageable pageable) {
        Assert.notNull(pageable, LogUtils.notNullFormat("pageable"));

        return CustomPageDtoFactory.createCustomPageDto(
                groupsRepository.findSlicedMyGroups(memberId, pageable)
                        .map(GroupDtoFactory::toGroupSummary));
    }


    public CustomPageDto<GroupsSummaryDto> getOtherGroupsSummary(long memberId, Pageable pageable) {
        Assert.notNull(pageable, LogUtils.notNullFormat("pageable"));

        return CustomPageDtoFactory.createCustomPageDto(
                groupsRepository.findSlicedOtherGroups(memberId, pageable)
                        .map(GroupDtoFactory::toGroupSummary));
    }

    @Transactional
    public GroupsDetailDto getGroupDetail(long groupId, long memberId) {
        if (!this.checkGroupAuthentication(groupId, memberId)) {
            throw new ForbiddenException();
        }

        Groups groups = groupsRepository.findGroupsWithMember(groupId)
                .orElseThrow(() -> { throw new DataNotFoundException(); });

        return GroupDtoFactory.toGroupDetail(groups);
    }

    public boolean checkGroupAuthentication(long groupId, long memberId) {
        return groupsRepository.findGroupsWithMember(groupId).filter(groups ->
                groups.getMemberList().stream()
                    .filter(member -> memberId == member.getMember().getId()).toList()
                    .size() > 0)
                .isPresent();
    }

    public List<GroupBoardSummaryDto> getBoardSummaryList(long groupId, long memberId) {
        if (!this.checkGroupAuthentication(groupId, memberId)) {
            throw new ForbiddenException();
        }

        List<GroupBoard> boardList = groupBoardRepository.findBoardListByGroupId(groupId);
        return boardList.stream()
                .map(GroupDtoFactory::toGroupBoardSummary)
                .toList();
    }

    public void addBoard(GroupBoardContents groupBoardContents, long memberId, long groupId) {
        Assert.notNull(groupBoardContents, LogUtils.notNullFormat("groupBoardContents"));

        if (!this.checkGroupAuthentication(groupId, memberId)) {
            throw new ForbiddenException();
        }

        Groups groups = new Groups();
        groups.setId(groupId);
        GroupBoard groupBoard = new GroupBoard();
        groupBoard.setTitle(groupBoardContents.getTitle());
        groupBoard.setContent(groupBoardContents.getContent());
        groupBoard.setLikeCount(0L);
        groupBoard.setGroups(groups);

        groupBoardRepository.save(groupBoard);
    }

    @Transactional
    public void updateBoard(GroupBoardContents groupBoardContents, long memberId, long groupId) {
        Assert.notNull(groupBoardContents, LogUtils.notNullFormat("groupBoardContents"));

        if (!this.checkGroupAuthentication(groupId, memberId)) {
            throw new ForbiddenException();
        }

        GroupBoard groupBoard = groupBoardRepository.findBoardById(groupBoardContents.getBoardId())
                .filter(board -> board.getGroups().getId().equals(groupId))
                .orElseThrow(() -> { throw new DataNotFoundException(); });

        groupBoard.setTitle(groupBoardContents.getTitle());
        groupBoard.setContent(groupBoardContents.getContent());
    }


    public void removeBoard(long boardId, long memberId) {
        GroupBoard groupBoard = groupBoardRepository.findBoardByBoardIdAndMemberId(boardId, memberId)
                .orElseThrow(() -> { throw new DataNotFoundException(); });

        groupBoardRepository.delete(groupBoard);
    }
}
