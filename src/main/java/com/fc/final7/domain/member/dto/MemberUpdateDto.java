package com.fc.final7.domain.member.dto;

import lombok.Data;

@Data
public class MemberUpdateDto {


    private String newPassword;
    private String validNewPassword;
    private String phone;


}
