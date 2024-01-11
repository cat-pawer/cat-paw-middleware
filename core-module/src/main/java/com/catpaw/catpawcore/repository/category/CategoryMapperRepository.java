package com.catpaw.catpawcore.repository.category;

import com.catpaw.catpawcore.domain.entity.CategoryMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMapperRepository extends CrudRepository<CategoryMapper, Long> {

}
