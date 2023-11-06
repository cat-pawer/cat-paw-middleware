package com.catpaw.catpawmiddleware.service.category;

import com.catpaw.catpawmiddleware.domain.entity.Category;
import com.catpaw.catpawmiddleware.domain.entity.CategoryMapper;
import com.catpaw.catpawmiddleware.domain.eumns.CategoryType;
import com.catpaw.catpawmiddleware.domain.eumns.TargetType;
import com.catpaw.catpawmiddleware.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Map<Long, List<CategoryMapper>> getMapByCategoryTypeList(List<Long> idList, TargetType targetType, List<CategoryType> categoryTypeList) {
        List<CategoryMapper> categoryMapperList = categoryRepository.findByInCategoryTypeList(idList, targetType, categoryTypeList);
        HashMap<Long, List<CategoryMapper>> mapper = new HashMap<>();
        for (Long id : idList) {
            mapper.put(id, categoryMapperList.stream()
                    .filter(categoryMapper -> categoryMapper.getTargetId().equals(id))
                    .toList());
        }
        return mapper;
    }


}
