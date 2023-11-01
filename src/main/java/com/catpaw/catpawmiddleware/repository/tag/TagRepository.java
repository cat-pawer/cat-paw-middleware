package com.catpaw.catpawmiddleware.repository.tag;

import com.catpaw.catpawmiddleware.domain.entity.Tag;
import com.catpaw.catpawmiddleware.domain.entity.TagRecruit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    @Query("SELECT tagRecruit FROM TagRecruit tagRecruit" +
            " JOIN FETCH Tag tag " +
            "WHERE tagRecruit.recruit.id IN :recruitIdList")
    List<TagRecruit> findListByRecruitIdList(@Param("recruitIdList") List<Long> recruitIdList);
}
