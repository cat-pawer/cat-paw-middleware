package com.catpaw.catpawmiddleware.repository.groups;

import com.catpaw.catpawcore.domain.entity.Groups;
import com.catpaw.catpawcore.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.repository.groups.query.GroupsQueryRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupsRepository extends CrudRepository<Groups, Long>, GroupsQueryRepository, GroupsRepositoryCustom {

}
