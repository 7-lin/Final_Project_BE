package com.fc.final7.domain.member.dto;

import com.fc.final7.domain.jwt.dto.TokenDto;
import com.fc.final7.domain.member.enums.Gender;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemberResponseDto {

    private String email;
    private String name;
    private Gender gender;
    private Integer age;
    private TokenDto tokenDto;
}
