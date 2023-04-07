package com.fc.final7.domain.member.entity;

import com.fc.final7.domain.member.dto.MemberDeleteDto;
import com.fc.final7.domain.member.dto.SignUpRequestDto;
import com.fc.final7.domain.member.enums.Gender;
import com.fc.final7.domain.member.enums.IsMember;
import com.fc.final7.domain.member.enums.Role;
import com.fc.final7.domain.member.recommend.entity.Survey;
import com.fc.final7.domain.reservation.entity.Reservation;
import com.fc.final7.global.entity.Auditing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.fc.final7.domain.member.enums.IsMember.NO;
import static com.fc.final7.domain.member.enums.IsMember.YES;
import static com.fc.final7.domain.member.service.MemberService.validationEmail;
import static com.fc.final7.domain.member.service.MemberService.validationPassword;
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

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<WishList> wishLists = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "suevey_id")
    private Survey survey;

    @Builder.Default
    @Column(name = "role", columnDefinition = "VARCHAR(64)")
    @Enumerated(STRING)
    private Role role = Role.MEMBER;

    @Column(name = "email",columnDefinition = "VARCHAR(255)", unique = true)
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(name = "name", columnDefinition = "VARCHAR(20)")
    private String name;

    @Column(name = "phone", columnDefinition = "VARCHAR(40)", unique = true)
    private String phone;

    @Column(name = "birth", columnDefinition = "DATE")
    private Date birth;

    @Column(name = "gender", columnDefinition = "VARCHAR(10)")
    @Enumerated(STRING)
    private Gender gender;

    @Column(name = "age")
    private Integer age;

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


    public static Member createMember(SignUpRequestDto memberDto, PasswordEncoder encoder) throws Exception {
        if (validationEmail(memberDto) == false) {
            throw new IllegalArgumentException();
        }
        if (validationPassword(memberDto) == false) {
            throw new IllegalArgumentException();
        }
        return Member.builder()
                .email(memberDto.getEmail())
                .password(encoder.encode(memberDto.getPassword()))
                .name(memberDto.getName())
                .age(memberDto.getAge())
                .gender(memberDto.getGender())
                .birth(memberDto.getBirth())
                .phone(memberDto.getPhone())
                .build();
    }

    public MemberDeleteDto deleteMember(String email){
        this.isMember = NO;
        return MemberDeleteDto.builder().email(email).isMember(NO).build();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }

}
