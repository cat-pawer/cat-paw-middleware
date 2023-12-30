package com.catpaw.catpawmiddleware.repository.groups;

import com.catpaw.catpawcore.domain.entity.GroupBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupBoardRepository extends JpaRepository<GroupBoard, Long> {


    @Query("SELECT groupBoard " +
            "FROM GroupBoard groupBoard " +
            "WHERE groupBoard.id = :boardId " +
            "AND groupBoard.isDelete = 'N' ")
    Optional<GroupBoard> findBoardById(@Param("boardId") Long boardId);

    @Query("SELECT groupBoard " +
            "FROM GroupBoard groupBoard " +
            "JOIN FETCH Groups groups " +
            "ON groups.id = groupBoard.groups.id AND groups.isDelete = 'N' " +
            "WHERE groupBoard.groups.id = :groupId AND groupBoard.isDelete = 'N'")
    List<GroupBoard> findBoardListByGroupId(@Param("groupId") Long groupId);

    @Query("SELECT groupBoard " +
            "FROM Groups groups " +
            "JOIN FETCH GroupBoard groupBoard " +
            "JOIN FETCH GroupMember groupMember " +
            "WHERE groupBoard.id = :boardId " +
            "AND groupMember.member.id = :memberId " +
            "AND groups.isDelete = 'N' " +
            "AND groupMember.isDelete ='N' " +
            "AND groupBoard.isDelete = 'N' ")
    Optional<GroupBoard> findBoardByBoardIdAndMemberId(@Param("boardId") Long boardId, @Param("memberId") Long memberId);
}
