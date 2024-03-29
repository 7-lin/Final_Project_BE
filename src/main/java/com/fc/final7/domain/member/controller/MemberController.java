package com.fc.final7.domain.member.controller;

import com.fc.final7.domain.jwt.JwtProperties;
import com.fc.final7.domain.jwt.JwtProvider;
import com.fc.final7.domain.jwt.dto.TokenDto;
import com.fc.final7.domain.member.dto.*;
import com.fc.final7.domain.member.service.MemberService;
import com.fc.final7.domain.member.service.SmtpEmailService;
import com.fc.final7.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final SmtpEmailService smtpEmailService;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestHeader("Authorization") String requestAccessToken) {
        if (!jwtProvider.validate(requestAccessToken)) {
            return ResponseEntity.status(HttpStatus.OK).build(); // 재발급 필요X
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 재발급 필요
        }
    }



    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@CookieValue(name = "refresh-token") String requestRefreshToken,
                                     @RequestHeader("Authorization") String requestAccessTokenInHeader) {

        TokenDto reissuedTokenDto = jwtProvider.reissue(requestAccessTokenInHeader, requestRefreshToken);

        if (reissuedTokenDto != null) { // 토큰 재발급 성공
            // RT 저장
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", reissuedTokenDto.getRefreshToken())
                    .maxAge(jwtProperties.getRefreshTokenValidTime())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    // AT 저장
                    .header(HttpHeaders.AUTHORIZATION, jwtProperties.getTokenPrefix() + reissuedTokenDto.getAccessToken())
                    .build();

        } else { // Refresh Token 탈취 가능성
            // Cookie 삭제 후 재로그인 유도
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                    .maxAge(0)
                    .path("/")
                    .sameSite("None")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .build();
        }
    }

    @PostMapping("/signUp")
    public BaseResponse<MemberResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto) throws Exception {
        MemberResponseDto memberResponseDto = memberService.signUp(requestDto);
        return BaseResponse.of(1, "회원가입이 완료되었습니다.", memberResponseDto);
    }

    @PostMapping("/signUp/checkId")
    public boolean checkDuplicationEmail(@RequestBody SignUpRequestDto requestDto) {
        return memberService.checkDuplicationEmail(requestDto);
    }

    @PostMapping("/signUp/checkPhone")
    public boolean checkDuplicationPhone(@RequestBody SignUpRequestDto requestDto) {
        return memberService.checkDuplicationPhone(requestDto);
    }

    @PostMapping("/member/update/checkPhone")
    public boolean checkDuplicationPhone(@RequestBody MemberUpdateDto updateDto){
        return memberService.checkDuplicationPhoneUpdate(updateDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginDto loginDto) {
        MemberResponseDtoInToken memberResponseDtoInToken = memberService.login(loginDto);

        HttpCookie httpCookie = ResponseCookie.from("refresh-token", memberResponseDtoInToken.getTokenDto().getRefreshToken())
                .maxAge(jwtProperties.getRefreshTokenValidTime())
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();

        ResponseEntity<MemberResponseDtoInToken> response = ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, httpCookie.toString())  // RT 저장
                // AT 저장
                .header(HttpHeaders.AUTHORIZATION, jwtProperties.getTokenPrefix() + memberResponseDtoInToken.getTokenDto().getAccessToken())
                .header(HttpHeaders.EXPIRES, jwtProperties.getAccessTokenValidTime().toString())
                .body(memberResponseDtoInToken);
        return  response;
//
    }

    @PutMapping("/member/update")
    public BaseResponse<Object> updateMember(@RequestBody MemberUpdateDto updateDto,
                                             @RequestHeader("Authorization") String requestAccessToken) {

        memberService.updateMember(updateDto, requestAccessToken);
        return BaseResponse.of("회원정보 수정에 성공했습니다", 1);
    }

    @GetMapping("/member")
    public BaseResponse<Object> MemberInfo(@RequestHeader("Authorization") String requestAccessToken) {
        MemberResponseDto memberResponseDto = memberService.getMemberInformation(requestAccessToken);
        return BaseResponse.of(1, "회원정보를 조회합니다.", memberResponseDto);
    }

    @GetMapping("/findId")
    public BaseResponse<FindMemberIdDto> findMemberId(@RequestParam String name, @RequestParam String phone) {
        FindMemberIdDto findMemberIdDto = memberService.findMemberId(name, phone);
        return BaseResponse.of(1, "아이디를 조회합니다.", findMemberIdDto);
    }

    @PutMapping("/deleteMember")
    public BaseResponse<Object> deleteMember(@RequestHeader("Authorization") String requestAccessToken) {
        MemberDeleteDto memberDeleteDto = memberService.deleteMember(requestAccessToken);
        return BaseResponse.of(1, "아이디를 삭제합니다.", memberDeleteDto);
    }


    @PostMapping("/sendEmail")
    public BaseResponse<MailDto> sendPasswordMail(@RequestParam String email, @RequestParam String phone) throws MessagingException {
        MailDto mail = smtpEmailService.createMail(email, phone);

        return BaseResponse.of(1, "임시 비밀번호를 발송했습니다.", mail);
    }

    @PostMapping("/logouts")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String requestAccessToken) {
        memberService.logout(requestAccessToken);
        ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                .maxAge(0)
                .path("/")
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.SET_COOKIE,  responseCookie.toString());


        return new ResponseEntity<>("로그아웃이 완료되었습니다.", httpHeaders, HttpStatus.OK);
    }


    @PostMapping("/auth/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);

            String redirectUri = request.getHeader("referer");
            response.sendRedirect(redirectUri);
        }
    }

}
