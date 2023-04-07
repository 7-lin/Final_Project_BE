package com.fc.final7.domain.member.recommend.service;

import com.fc.final7.domain.member.recommend.dto.SurveyRequestDto;
import com.fc.final7.domain.member.recommend.dto.SurveyResponseDto;
import com.fc.final7.domain.member.recommend.entity.Survey;
import com.fc.final7.domain.member.recommend.repository.SurveyRepository;;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    @Transactional
    public SurveyResponseDto saveSurvey(SurveyRequestDto dto) {
        Survey survey = Survey.createSurveyQuestion(dto);
        Survey saved = surveyRepository.save(survey);
        return from(saved);
    }


    public SurveyResponseDto from(Survey survey) {
        return SurveyResponseDto.builder()
                .name(survey.getName())
                .phone(survey.getPhone())
                .ageGroup(survey.getAgeGroup())
                .companionGroup(survey.getCompanion())
                .travelGroup(survey.getTravelGroup())
                .politics(survey.getPolitics())
                .religion(survey.getReligion())
                .travelPeriod(survey.getTravelPeriod())
                .travelNumber(survey.getTravelNumber())
                .travelTheme(survey.getTravelTheme())
                .build();
    }
}


