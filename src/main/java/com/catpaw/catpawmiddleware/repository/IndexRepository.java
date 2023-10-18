package com.catpaw.catpawmiddleware.repository;

import com.catpaw.catpawmiddleware.domain.entity.Index;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexRepository extends CrudRepository<Index, Long> {
}
