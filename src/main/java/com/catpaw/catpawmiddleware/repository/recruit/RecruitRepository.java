package com.catpaw.catpawmiddleware.repository.recruit;

import com.catpaw.catpawmiddleware.domain.entity.Recruit;
import com.catpaw.catpawmiddleware.repository.recruit.query.RecruitQueryRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RecruitRepository extends CrudRepository<Recruit, Long>, RecruitQueryRepository, RecruitRepositoryCustom {

}
