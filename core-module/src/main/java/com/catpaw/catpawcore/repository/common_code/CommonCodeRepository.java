package com.catpaw.catpawcore.repository.common_code;

import com.catpaw.catpawcore.domain.entity.CommonCode;
import com.catpaw.catpawcore.domain.eumns.CodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, Long> {

    List<CommonCode> findByType(CodeType codeType);
}
