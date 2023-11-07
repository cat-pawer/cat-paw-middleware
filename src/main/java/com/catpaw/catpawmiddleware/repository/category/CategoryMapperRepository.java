package com.catpaw.catpawmiddleware.repository.category;

import com.catpaw.catpawmiddleware.domain.entity.CategoryMapper;
import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.repository.condition.RecruitSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CategoryMapperRepository extends CrudRepository<CategoryMapper, Long> {

}