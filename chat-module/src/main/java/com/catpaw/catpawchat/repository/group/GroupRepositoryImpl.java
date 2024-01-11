package com.catpaw.catpawchat.repository.group;

import com.catpaw.catpawchat.service.dto.ScheduleDto;
import com.catpaw.catpawchat.service.dto.ScheduleSummaryDto;
import com.catpaw.catpawcore.domain.entity.Groups;
import com.catpaw.catpawcore.domain.entity.Schedule;
import com.catpaw.catpawcore.domain.entity.ScheduleSummary;
import com.catpaw.catpawcore.domain.eumns.IsDelete;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.catpaw.catpawcore.domain.entity.QGroups.groups;
import static com.catpaw.catpawcore.domain.entity.QSchedule.schedule;
import static com.catpaw.catpawcore.domain.entity.QScheduleSummary.scheduleSummary;

@Repository
public class GroupRepositoryImpl implements GroupRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public GroupRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Groups> findGroupListByMemberId(Long memberId) {
        return em.createQuery("SELECT groups " +
                        "FROM GroupMember groupMember " +
                        "JOIN FETCH Groups groups " +
                        "JOIN FETCH Member member " +
                        "WHERE groupMember.member.id = :memberId " +
                        "AND groupMember.groups.state = com.catpaw.catpawcore.domain.eumns.GroupState.ACTIVE",
                        Groups.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public List<ScheduleDto> findScheduleSummaryByGroupId(long groupId) {

        List<Tuple> tuples = queryFactory.select(schedule, scheduleSummary)
                .from(groups)
                .leftJoin(schedule)
                .on(schedule.groups.id.eq(groups.id), schedule.isDelete.eq(IsDelete.NO.getValue()))
                .leftJoin(scheduleSummary)
                .on(scheduleSummary.schedule.id.eq(schedule.id), scheduleSummary.isDelete.eq(IsDelete.NO.getValue()))
                .where(groups.id.eq(groupId))
                .fetch();

        List<ScheduleDto> scheduleDtoList = new ArrayList<>();

        for (Tuple tuple : tuples) {
            Schedule scheduleEntity = tuple.get(schedule);
            ScheduleSummary scheduleSummaryEntity = tuple.get(scheduleSummary);

            Optional<ScheduleDto> findOne = scheduleDtoList.stream()
                    .filter(scheduleDto -> scheduleDto.getId().equals(scheduleEntity.getId()))
                    .findAny();

            if (findOne.isPresent()) {
                if (scheduleSummaryEntity != null) findOne.get().addScheduleSummaryDto(new ScheduleSummaryDto(scheduleSummaryEntity));
            }
            else {
                if (scheduleEntity != null) {
                    ScheduleDto scheduleDto = new ScheduleDto(scheduleEntity);
                    scheduleDtoList.add(scheduleDto);
                    if (scheduleSummaryEntity != null) scheduleDto.addScheduleSummaryDto(new ScheduleSummaryDto(scheduleSummaryEntity));
                }
            }
        }

        return scheduleDtoList;
    }
}
