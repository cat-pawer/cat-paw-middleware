//package com.catpaw.catpawmiddleware.service.tag;
//
//import com.catpaw.catpawmiddleware.repository.tag.TagRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class TagService {
//
//    private final TagRepository tagRepository;
//
//    public Map<Long, List<Tag>> getTagMapByRecruitIdList(List<Long> recruitIdList) {
//        List<TagRecruit> findTagList = tagRepository.findListByRecruitIdList(recruitIdList);
//
//        HashMap<Long, List<Tag>> mapper = new HashMap<>();
//        for (Long id : recruitIdList) {
//            mapper.put(id, findTagList.stream()
//                    .filter(tag -> tag.getRecruit().getId().equals(id))
//                    .map(TagRecruit::getTag).toList());
//        }
//
//        return mapper;
//    }
//
//}
