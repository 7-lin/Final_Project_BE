package com.fc.final7.domain.member.recommend.entity;

import com.fc.final7.domain.member.recommend.dto.SurveyRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "Survey")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class Survey {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "survey_id")
    private Long id;

//    @OneToOne(fetch = LAZY)
//    @JoinColumn(name = "surveyor_id")
//    private Surveyor surveyor;

//    @Column(name = "privacy", columnDefinition = "TINYINT")
//    @Enumerated(STRING)
//    private Collection collection;
//
//    @Column(name = "privacy_3rd", columnDefinition = "TINYINT")
//    @Enumerated(STRING)
//    private Collection collection_3rd;

    @Column(name = "name", columnDefinition = "VARCHAR(10)")
    private String name;

    @Column(name = "phone", columnDefinition = "VARCHAR(20)")
    private String phone;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable
    private List<String> ageGroup = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable
    private List<String> companion = new ArrayList<>(); // 동행자 유형

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable
    private List<String> travelGroup = new ArrayList<>();  // 그룹유형

    @Column(name = "religion", columnDefinition = "VARCHAR(20)")
    private String religion;

    @Column(name = "politics", columnDefinition = "VARCHAR(20)")
    private String politics;

    @Column(name = "travelNumber", columnDefinition = "VARCHAR(20)")
    private String travelNumber; // 재정상황

    @Column(name = "travelTheme", columnDefinition = "VARCHAR(20)")
    private String travelTheme; // 여행 테마

    @Column(name = "travelPeriod", columnDefinition = "VARCHAR(20)")
    private String travelPeriod; // 선호하는 여행기간


    public static Survey createSurveyQuestion(SurveyRequestDto dto) {
        return Survey.builder()
                .name(dto.getName())
                .phone(dto.getPhone())
                .ageGroup(dto.getAgeGroup())
                .companion(dto.getCompanionGroup())
                .travelGroup(dto.getTravelGroup())
                .travelNumber(dto.getTravelNumber())
                .travelTheme(dto.getTravelTheme())
                .travelPeriod(dto.getTravelPeriod())
                .religion(dto.getReligion())
                .politics(dto.getPolitics())
                .build();
    }

}
