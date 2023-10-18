package com.catpaw.catpawmiddleware.service;

import com.catpaw.catpawmiddleware.domain.entity.Index;
import com.catpaw.catpawmiddleware.repository.IndexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndexService {

    private final IndexRepository indexRepository;

    public Index service() {
        Index index = new Index();
        index.setName("hello");

        Index saved = indexRepository.save(index);

        return indexRepository.findById(saved.getId()).get();
    }
}
