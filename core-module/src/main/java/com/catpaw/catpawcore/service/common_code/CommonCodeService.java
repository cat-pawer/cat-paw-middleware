package com.catpaw.catpawcore.service.common_code;

import com.catpaw.catpawcore.domain.entity.CommonCode;
import com.catpaw.catpawcore.domain.eumns.CodeType;
import com.catpaw.catpawcore.repository.common_code.CommonCodeRepository;
import com.catpaw.catpawcore.service.dto.CommonCodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;

    public List<CommonCodeDto> getCodeListByType(CodeType codeType) {
        List<CommonCode> codeList = commonCodeRepository.findByType(codeType);

        return codeList.stream()
                .map(CommonCodeDto::new)
                .toList();
    }
}
