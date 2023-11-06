package com.catpaw.catpawmiddleware.service.dto.recruit;

import com.catpaw.catpawmiddleware.domain.eumns.GroupType;
import com.catpaw.catpawmiddleware.domain.eumns.OnlineType;
import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;


@Getter @Setter
public class RecruitSearchDto {

    private String searchValue;

    private GroupType recruitType;

    private OnlineType onlineType;

    private RecruitState state;

    private LocalDate recruitPeriod;

    private List<Long> categoryIdList;

    public boolean isEmpty() {
        return
                !StringUtils.hasText(searchValue)
                        && recruitType == null
                        && onlineType == null
                        && state == null
                        && (categoryIdList == null || categoryIdList.isEmpty());
    }
}
