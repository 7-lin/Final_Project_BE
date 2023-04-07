package com.fc.final7.domain.member.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurveyResponseDto {

    private String name;
    private String phone;

    private List<String> ageGroup = new ArrayList<>();
    private List<String> travelGroup = new ArrayList<>();
    private List<String> companionGroup = new ArrayList<>();
    private String religion;
    private String politics;
    private String travelTheme;
    private String travelPeriod;
    private String travelNumber;

}
