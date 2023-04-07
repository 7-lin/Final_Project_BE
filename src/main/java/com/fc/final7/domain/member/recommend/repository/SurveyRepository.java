package com.fc.final7.domain.member.recommend.repository;

import com.fc.final7.domain.member.recommend.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

}
