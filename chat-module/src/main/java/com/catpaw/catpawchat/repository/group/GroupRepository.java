package com.catpaw.catpawchat.repository.group;

import com.catpaw.catpawcore.domain.entity.Groups;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Groups, Long>, GroupRepositoryCustom {
}
