package com.fc.final7.domain.member.dto;

import com.fc.final7.domain.jwt.dto.TokenDto;
import com.fc.final7.domain.member.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemberResponseDtoInToken {

    private String email;
    private String name;
    private Gender gender;
    private Integer age;
    private String phone;
    private TokenDto tokenDto;



}
