package com.fc.final7.domain.recommend.entity;

import com.fc.final7.domain.member.entity.Gender;
import com.fc.final7.global.entity.Auditing;
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
@Table(name = "group_recommend")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class GroupRecommend extends Auditing {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "group_recommend_id")
    private Long id;

    @OneToOne(mappedBy = "groupRecommend", fetch = LAZY)
    private GroupRecommendContent groupRecommendContent;

    @Column(name = "phone", columnDefinition = "VARCHAR(40)")
    private String phone;

    @Column(name = "name", columnDefinition = "VARCHAR(20)")
    private String name;

    @Column(name = "gender", columnDefinition = "VARCHAR(10)")
    @Enumerated(STRING)
    private Gender gender;

    @Column(name = "writer", columnDefinition = "VARCHAR(20)")
    private String writer;
}
