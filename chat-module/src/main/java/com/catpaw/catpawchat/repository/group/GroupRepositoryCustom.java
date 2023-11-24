package com.catpaw.catpawchat.repository.group;

import com.catpaw.catpawcore.domain.entity.Groups;

import java.util.List;

public interface GroupRepositoryCustom {


    List<Groups> findGroupListByMemberId(Long memberId);
}
