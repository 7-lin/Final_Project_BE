package com.fc.final7.domain.member.dto;

import com.fc.final7.domain.member.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDto {

    @NotNull(message = "이메일을 입력해주세요")
    private String email;

    @NotNull(message = "비밀번호를 입력해주세요")
    private String password;

    @NotNull(message = "비밀번호를 입력해주세요")
    private String validPassword;

    @NotNull(message = "이름을 입력해주세요")
    private String name;

    @NotNull(message = "휴대폰 번호를 입력해주세요")
    private String phone;

    @NotNull(message = "생일을 입력해주세요")
    private Date birth;

    @NotNull(message = "성별을 입력해주세요")
    private Gender gender;

    @NotNull(message = "나이를 입력해주세요")
    private Integer age;

}
