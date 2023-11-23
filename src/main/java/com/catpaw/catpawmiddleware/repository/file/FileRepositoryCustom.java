package com.catpaw.catpawmiddleware.repository.file;

import com.catpaw.catpawmiddleware.domain.entity.FileMaster;
import com.catpaw.catpawmiddleware.service.dto.file.FileTarget;

import java.util.List;

public interface FileRepositoryCustom {

    List<FileMaster> findFileMasterByFileTarget(FileTarget fileTarget);
}
