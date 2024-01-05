package com.catpaw.catpawmiddleware.repository.groups;

import com.catpaw.catpawcore.domain.entity.Groups;
import com.catpaw.catpawmiddleware.repository.groups.query.GroupsQueryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GroupsRepository extends CrudRepository<Groups, Long>, GroupsQueryRepository, GroupsRepositoryCustom {

    @Query("SELECT groups " +
            "FROM Groups groups " +
            "LEFT JOIN FETCH groups.memberList groupMember " +
            "INNER JOIN FETCH groupMember.member member " +
            "WHERE groups.id = :groupId " +
            "AND groups.isDelete = 'N' AND groupMember.state = com.catpaw.catpawcore.domain.eumns.GroupMemberState.JOIN")
    Optional<Groups> findGroupsWithMember(@Param("groupId") Long groupId);
}
