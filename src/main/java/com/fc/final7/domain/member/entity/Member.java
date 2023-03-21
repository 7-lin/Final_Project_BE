package com.fc.final7.domain.member.entity;

import com.fc.final7.domain.reservation.entity.Reservation;
import com.fc.final7.global.entity.Auditing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fc.final7.domain.member.entity.IsMember.YES;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends Auditing {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<WishList> wishLists = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservations = new ArrayList<>();

    // TODO: 2023-03-21 ref 멤버는 어떻게 정해야 할지 몰라서 냅둠

    @Column(name = "role", columnDefinition = "VARCHAR(64)")
    private String role;

    @Column(name = "email",columnDefinition = "VARCHAR(255)", unique = true)
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(name = "name", columnDefinition = "VARCHAR(20)")
    private String name;

    @Column(name = "phone", columnDefinition = "VARCHAR(40)")
    private String phone;

    @Column(name = "birth", columnDefinition = "DATE")
    private Date birth;

    @Column(name = "gender", columnDefinition = "VARCHAR(10)")
    @Enumerated(STRING)
    private Gender gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "thumbnail", columnDefinition = "TEXT")
    private String thumbnail;

    @Column(name = "category_group", columnDefinition = "VARCHAR(60)")
    private String group;

    @Column(name = "category_theme", columnDefinition = "VARCHAR(60)")
    private String theme;

    @Column(name = "price")
    private Long price;

    @Builder.Default
    @Column(name = "status", columnDefinition = "VARCHAR(10) DEFAULT 'YES'")
    @Enumerated(STRING)
    private IsMember isMember = YES;
}
