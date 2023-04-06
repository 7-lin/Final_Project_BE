package com.fc.final7.domain.member.service;

import com.fc.final7.domain.jwt.JwtProvider;
import com.fc.final7.domain.jwt.dto.TokenDto;
import com.fc.final7.domain.member.dto.*;
import com.fc.final7.domain.member.entity.Member;
import com.fc.final7.domain.member.enums.IsMember;
import com.fc.final7.domain.member.repository.MemberRepository;
import com.fc.final7.global.exception.EXIST_MEMBER;
import com.fc.final7.global.exception.PasswordNotMatchException;
import com.fc.final7.global.exception.TokenExpirationException;
import com.fc.final7.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @Transactional
    public MemberResponseDto signUp(SignUpRequestDto requestDto) throws Exception {
        if (checkDuplicationEmail(requestDto)) {
            throw new EXIST_MEMBER();
        }
        if (checkDuplicationPhone(requestDto)){
            throw new EXIST_MEMBER();
        }
        if (!requestDto.getPassword().equals(requestDto.getValidPassword())) {
            throw new PasswordNotMatchException();
        }

        Member member = Member.createMember(requestDto, encoder);
        memberRepository.save(member);
        return MemberResponseDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .age(member.getAge())
                .phone(member.getPhone())
                .gender(member.getGender())
                .build();
    }


    public MemberResponseDtoInToken login(MemberLoginDto loginDto) {

        Member member = memberRepository.findByEmail(loginDto.getEmail()).orElseThrow(EntityNotFoundException::new);

        if (member.getIsMember() == IsMember.NO) {
            throw new EntityNotFoundException();
        }

        if (encoder.matches(loginDto.getPassword(), member.getPassword())) {
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
//
//            Authentication authentication = authenticationManagerBuilder.getObject()
//                    .authenticate(authenticationToken);
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            TokenDto tokenDto = jwtProvider.generateToken(member.getName(), "ROLE_MEMBER");

            return MemberResponseDtoInToken.builder()
                    .tokenDto(tokenDto)
                    .email(member.getEmail())
                    .name(member.getName())
                    .gender(member.getGender())
                    .age(member.getAge())
                    .phone(member.getPhone())
                    .build();

        } else {
            throw new PasswordNotMatchException();
        }
    }

    //    @Transactional
//    public TokenDto login(MemberLoginDto loginDto) {
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
//
//        Authentication authentication = authenticationManagerBuilder.getObject()
//                .authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return jwtProvider.generateToken(authentication.getName(), jwtProvider.getAuthorities(authentication));
//    }
    @Transactional
    public MemberUpdateResponseDto updateMember(MemberUpdateDto updateDto, String email, String requestAccessTokenInHeader) {

        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
//        String refreshTokenIdRedis = redisService.getValues("RT : " + member.getEmail());
//
//        if (jwtProvider.validate(requestAccessTokenInHeader)){
//            jwtProvider.reissue(requestAccessTokenInHeader, refreshTokenIdRedis);
//        }else {
//            throw new TokenExpirationException();
//        }

        if (updateDto.getPhone() == null && updateDto.getNewPassword() != null && updateDto.getValidNewPassword() != null) {
            if (updateDto.getNewPassword().equals(updateDto.getValidNewPassword())) {
                member.updatePassword(encoder.encode(updateDto.getNewPassword()));
                member.updatePhone(member.getPhone());
            } else {
                throw new PasswordNotMatchException();
            }
        }
        if (updateDto.getPhone() != null && updateDto.getNewPassword() == null && updateDto.getValidNewPassword() == null) {
            member.updatePhone(updateDto.getPhone());
            member.updatePassword(encoder.encode(member.getPassword()));
        }
        if (updateDto.getPhone() != null && updateDto.getNewPassword() != null && updateDto.getValidNewPassword() != null) {
            if (updateDto.getNewPassword().equals(updateDto.getValidNewPassword())) {
                member.updatePassword(encoder.encode(updateDto.getNewPassword()));
                member.updatePhone(updateDto.getPhone());
            }
        }
        memberRepository.save(member);
        return MemberUpdateResponseDto.builder()
                .password(updateDto.getNewPassword())
                .phone(updateDto.getPhone())
                .build();
    }

    public MemberResponseDto getMemberInformation(String email, String requestAccessTokenInHeader) {
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
//        String refreshTokenIdRedis = redisService.getValues("RT : " + member.getEmail());
//
//        if (jwtProvider.validate(requestAccessTokenInHeader)){
//            jwtProvider.reissue(requestAccessTokenInHeader, refreshTokenIdRedis);
//        }else {
//            throw new TokenExpirationException();
//        }

        return from(member);
    }


    @Transactional
    public MemberDeleteDto deleteMember(String email, String requestAccessTokenInHeader) {  // 회원탈퇴
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
//
//        if (!jwtProvider.validate(requestAccessTokenInHeader)) {
//            throw new TokenExpirationException();
//        }
//        String refreshTokenIdRedis = redisService.getValues("RT : " + member.getEmail());
//        redisService.deleteValues(refreshTokenIdRedis);
//        redisService.deleteValues("AT : " + member.getEmail());

//        memberRepository.delete(member);  // 회원정보 DB에서 삭제
        return member.deleteMember(email);
    }

//    @Transactional
//    public void logout(String requestAccessTokenInHeader, String email) {
//        String requestAccessToken = jwtProvider.resolveToken(requestAccessTokenInHeader);
//        String principal = jwtProvider.getPrincipal(requestAccessToken);
//
//        // Redis에 저장되어 있는 RT 삭제
//        String refreshTokenInRedis = redisService.getValues("RT :" + principal);
//        if (refreshTokenInRedis != null) {
//            redisService.deleteValues("RT :" + principal);
//        }
//
//        // Redis에 로그아웃 처리한 AT 저장
//        long expiration = jwtProvider.getTokenExpirationTime(requestAccessToken) - new Date().getTime();
//        redisService.setValuesWithTimeout(requestAccessToken, "logout", expiration, TimeUnit.SECONDS);
//    }


    public MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .gender(member.getGender())
                .age(member.getAge())
                .phone(member.getPhone())
                .build();
    }


    public FindMemberIdDto findMemberId(String name, String phone) {
        Member member = memberRepository.findByNameAndPhone(name, phone).orElseThrow(EntityNotFoundException::new);
        return FindMemberIdDto.builder().email(member.getEmail()).build();
    }

    public boolean checkDuplicationEmail(SignUpRequestDto requestDto) {
        return memberRepository.existsByEmail(requestDto.getEmail());
    }

    public boolean checkDuplicationPhone(SignUpRequestDto requestDto) {
        return memberRepository.existsByPhone(requestDto.getPhone());
    }

    public static final Pattern isEmail = Pattern.compile("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");

    public static final Pattern isPassword = Pattern.compile("^.{8,16}$");

    public static boolean validationEmail(SignUpRequestDto dto) {
        return isEmail.matcher(dto.getEmail()).matches();
    }

    public static boolean validationPassword(SignUpRequestDto dto) {
        return isPassword.matcher(dto.getPassword()).matches();
    }
}
