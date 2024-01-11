package com.catpaw.catpawcore.repository.category;

import com.catpaw.catpawcore.domain.entity.Category;
import com.catpaw.catpawcore.domain.entity.CategoryMapper;
import com.catpaw.catpawcore.domain.eumns.CategoryType;
import com.catpaw.catpawcore.domain.eumns.TargetType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long>, CategoryRepositoryCustom {


    @Query("SELECT categoryMapper FROM CategoryMapper categoryMapper" +
            " JOIN FETCH categoryMapper.category" +
            " WHERE categoryMapper.targetId IN :targetIdList AND categoryMapper.type = :targetType AND category.type IN :categoryTypeList")
    List<CategoryMapper> findByInCategoryTypeList(
            @Param("targetIdList") List<Long> targetIdList,
            @Param("targetType") TargetType targetType,
            @Param("categoryTypeList")List<CategoryType> categoryTypeList);
}
