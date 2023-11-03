package com.catpaw.catpawmiddleware.service.dto.recruit;

import com.catpaw.catpawmiddleware.domain.eumns.GroupType;
import com.catpaw.catpawmiddleware.domain.eumns.OnlineType;
import com.catpaw.catpawmiddleware.domain.eumns.RecruitState;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class RecruitSearchDto {

    private String title;

    private GroupType recruitType;

    private OnlineType onlineType;

    private RecruitState state;

    private Long tagId;

    private Long categoryId;
}
