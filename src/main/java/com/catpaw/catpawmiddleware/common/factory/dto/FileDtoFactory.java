package com.catpaw.catpawmiddleware.common.factory.dto;

import com.catpaw.catpawmiddleware.domain.entity.FileMaster;
import com.catpaw.catpawmiddleware.service.dto.file.FileDetailDto;
import com.catpaw.catpawmiddleware.service.dto.file.FileSummaryDto;


public class FileDtoFactory {

    public static FileSummaryDto toFileSummary(FileMaster fileMaster) {
        FileSummaryDto fileSummaryDto = new FileSummaryDto();
        fileSummaryDto.setId(fileMaster.getId());
        fileSummaryDto.setFileOriginalName(fileMaster.getFileOriginalName());
        fileSummaryDto.setAbsoluteDestination(fileMaster.getAbsoluteDestination());

        return fileSummaryDto;
    }

    public static FileDetailDto toFileDetail(FileMaster fileMaster) {
        FileDetailDto fileDetailDto = new FileDetailDto();
        fileDetailDto.setId(fileMaster.getId());
        fileDetailDto.setTargetId(fileMaster.getTargetId());
        fileDetailDto.setTargetUUID(fileMaster.getTargetUUID());
        fileDetailDto.setType(fileMaster.getType());
        fileDetailDto.setFileOriginalName(fileMaster.getFileOriginalName());
        fileDetailDto.setAbsoluteDestination(fileMaster.getAbsoluteDestination());
        fileDetailDto.setFileKey(fileMaster.getFileKey());

        return fileDetailDto;
    }
}
