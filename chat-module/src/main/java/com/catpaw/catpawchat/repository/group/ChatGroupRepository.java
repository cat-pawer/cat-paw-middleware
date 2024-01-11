package com.catpaw.catpawchat.repository.group;

import com.catpaw.catpawcore.domain.entity.GroupMember;
import com.catpaw.catpawcore.domain.entity.Groups;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatGroupRepository extends CrudRepository<Groups, Long>, GroupRepositoryCustom {

    @Query("SELECT groupMember " +
            "FROM GroupMember groupMember " +
            "JOIN FETCH groupMember.groups groups " +
            "WHERE groupMember.member.id = :memberId " +
            "AND groupMember.groups.id = :groupId " +
            "AND groupMember.state = com.catpaw.catpawcore.domain.eumns.GroupMemberState.JOIN")
    Optional<GroupMember> findMemberOfGroups(@Param("memberId") Long memberId, @Param("groupId") Long groupId);

    @Query("SELECT groupMember " +
            "FROM GroupMember groupMember " +
            "JOIN FETCH groupMember.groups groups " +
            "JOIN FETCH groupMember.member member " +
            "WHERE groupMember.state = com.catpaw.catpawcore.domain.eumns.GroupMemberState.JOIN " +
            "AND groupMember.groups.id = :groupId")
    List<GroupMember> findGroupMemberList(@Param("groupId") Long groupId);
}
