package com.catpaw.catpawmiddleware.repository.file;

import com.catpaw.catpawcore.domain.entity.FileMaster;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends CrudRepository<FileMaster, Long>, FileRepositoryCustom {


}
