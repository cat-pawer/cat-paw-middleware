package com.catpaw.catpawmiddleware.repository.groups;

import com.catpaw.catpawcore.domain.entity.GroupMember;
import com.catpaw.catpawcore.domain.entity.Groups;
import com.catpaw.catpawmiddleware.repository.groups.query.GroupsQueryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GroupsRepository extends CrudRepository<Groups, Long>, GroupsQueryRepository, GroupsRepositoryCustom {

    @Query("SELECT groupMember " +
            "FROM GroupMember groupMember " +
            "WHERE groupMember.member.id = :memberId " +
            "AND groupMember.groups.id = :groupId " +
            "AND groupMember.isDelete = 'N'")
    Optional<GroupMember> findGroupMemberByGroupIdAndMemberId(@Param("memberId") Long memberId, @Param("groupId") Long groupId);

    @Query("SELECT groups " +
            "FROM Groups groups " +
            "JOIN FETCH GroupMember groupMember " +
            "JOIN Member member ON member.id = groupMember.member.id AND member.isDelete = 'N'" +
            "WHERE groups.id = :groupId " +
            "AND groups.isDelete = 'N'")
    Optional<Groups> findGroupsWithMember(@Param("groupId") Long groupId);

}
