package com.fc.final7.domain.member.recommend.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Setter
@ToString
@Builder
public class SurveyRequestDto {
    /**
     * 연령대  (중복선택)
     * 그룹유형 (중복선택)
     * 동행자 유형 (중복선택)
     * 종교성향
     * 정치성향
     * 여행테마
     * 여행 시기
     * 연평균 여행 횟수 (재정상황)
     **/

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

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public List<String> getAgeGroup() {
        return ageGroup;
    }

    public List<String> getTravelGroup() {
        return travelGroup;
    }

    public List<String> getCompanionGroup() {
        return companionGroup;
    }

    public String getReligion() {
        return religion;
    }

    public String getPolitics() {
        return politics;
    }

    public String getTravelTheme() {
        return travelTheme;
    }

    public String getTravelPeriod() {
        return travelPeriod;
    }

    public String getTravelNumber() {
        return travelNumber;
    }
}
