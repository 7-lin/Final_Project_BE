package com.fc.final7.domain.recommend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "group_recommend_content")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class GroupRecommendContent {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "group_recommend_content_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "group_recommend_id")
    private GroupRecommend groupRecommend;

    @Column(name = "privacy", columnDefinition = "TINYINT")
    @Enumerated(STRING)
    private Collection collection;

    @Column(name = "privacy_3rd", columnDefinition = "TINYINT")
    @Enumerated(STRING)
    private Collection collection_3rd;

    @Column(name = "age", columnDefinition = "VARCHAR(10)")
    private Integer age;

    @Column(name = "religion", columnDefinition = "VARCHAR(20)")
    private String religion;

    @Column(name = "politics", columnDefinition = "VARCHAR(20)")
    private String politics;

    @Column(name = "financial", columnDefinition = "VARCHAR(20)")
    private String financial;

    @Column(name = "companion", columnDefinition = "VARCHAR(20)")
    private String companion;

}
