package com.catpaw.catpawmiddleware.repository.file;

import com.catpaw.catpawcore.domain.entity.FileMaster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<FileMaster, Long>, FileRepositoryCustom {

}
