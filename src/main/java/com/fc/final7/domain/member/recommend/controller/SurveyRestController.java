package com.fc.final7.domain.member.recommend.controller;

import com.fc.final7.domain.member.recommend.dto.SurveyRequestDto;
import com.fc.final7.domain.member.recommend.dto.SurveyResponseDto;
import com.fc.final7.domain.member.recommend.service.SurveyService;
import com.fc.final7.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SurveyRestController {

    private final SurveyService surveyService;

    @PostMapping("/survey")
    public BaseResponse<SurveyResponseDto> saveSurvey(@RequestBody SurveyRequestDto requestDto){
        SurveyResponseDto surveyResponseDto = surveyService.saveSurvey(requestDto);
        return BaseResponse.of(1, "설문조사를 완료했습니다", surveyResponseDto);
    }
}
