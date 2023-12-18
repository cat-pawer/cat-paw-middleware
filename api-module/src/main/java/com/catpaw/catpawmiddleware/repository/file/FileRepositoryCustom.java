package com.catpaw.catpawmiddleware.repository.file;

import com.catpaw.catpawcore.domain.dto.repository.PortFolioDto;
import com.catpaw.catpawcore.domain.entity.FileMaster;
import com.catpaw.catpawcore.domain.dto.service.file.FileTarget;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FileRepositoryCustom {

    List<FileMaster> findFileMasterByFileTarget(FileTarget fileTarget);

    PortFolioDto findMainPortFolio(long memberId);
}
