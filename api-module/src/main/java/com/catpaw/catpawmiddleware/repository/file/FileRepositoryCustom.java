package com.catpaw.catpawmiddleware.repository.file;

import com.catpaw.catpawcore.domain.entity.FileMaster;
import com.catpaw.catpawcore.domain.dto.service.file.FileTarget;

import java.util.List;

public interface FileRepositoryCustom {

    List<FileMaster> findFileMasterByFileTarget(FileTarget fileTarget);
}
