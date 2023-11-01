package com.catpaw.catpawmiddleware.repository.category;

import com.catpaw.catpawmiddleware.domain.entity.Category;
import com.catpaw.catpawmiddleware.domain.entity.CategoryMapper;
import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {


    @Query("SELECT categoryMapper FROM CategoryMapper categoryMapper" +
            " JOIN FETCH Category category" +
            " WHERE categoryMapper.targetId IN :targetIdList AND categoryMapper.type = :targetType")
    List<CategoryMapper> findListByTargetIdListAndTargetType(@Param("targetIdList") List<Long> targetIdList, @Param("targetType") TargetType targetType);
}
