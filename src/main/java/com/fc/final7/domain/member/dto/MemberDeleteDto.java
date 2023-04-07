package com.fc.final7.domain.member.dto;

import com.fc.final7.domain.member.enums.IsMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDeleteDto {

    private String email;
    private IsMember isMember;
}
