package com.fc.final7.domain.member.controller;

import com.fc.final7.domain.member.dto.MemberLoginDto;
import com.fc.final7.domain.member.dto.MemberResponseDto;
import com.fc.final7.domain.member.dto.SignUpRequestDto;
import com.fc.final7.domain.member.service.MemberService;
import com.fc.final7.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public BaseResponse<MemberResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestDto) throws Exception {
        MemberResponseDto memberResponseDto = memberService.signUp(requestDto);
        return BaseResponse.of(1, "회원가입이 완료되었습니다.", memberResponseDto);
    }

    @PostMapping("/signUp/checkId")
    public boolean checkDuplicationEmail(@RequestBody SignUpRequestDto requestDto) {
       return memberService.checkDuplicationEmail(requestDto);
    }

    @PostMapping("/login")
    public BaseResponse<MemberResponseDto> login(@RequestBody MemberLoginDto loginDto){
        MemberResponseDto memberResponseDto = memberService.login(loginDto);
        return BaseResponse.of(1, "로그인에 성공했습니다", memberResponseDto);
    }
}
