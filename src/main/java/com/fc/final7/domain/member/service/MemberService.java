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
            throw new EntityNotFoundException();      // 탈퇴한 회원일떄
        }

        if (encoder.matches(loginDto.getPassword(), member.getPassword())) {

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject()
                    .authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            TokenDto tokenDto = jwtProvider.generateToken(member.getEmail(), authentication.getAuthorities().toString());

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

    @Transactional
    public MemberUpdateResponseDto updateMember(MemberUpdateDto updateDto,  String requestAccessTokenInHeader) {

        String token = jwtProvider.resolveToken(requestAccessTokenInHeader);
        String email = jwtProvider.getClaimsFromToken(token).get("email", String.class);
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);

        String refreshTokenIdRedis = redisService.getValues("RT : " + member.getEmail());

        if (jwtProvider.validate(requestAccessTokenInHeader)){
            throw new TokenExpirationException();
        }else {
            jwtProvider.reissue(requestAccessTokenInHeader, refreshTokenIdRedis);
        }

        if (updateDto.getPhone() == null && updateDto.getNewPassword() != null && updateDto.getValidNewPassword() != null) {
            if (updateDto.getNewPassword().equals(updateDto.getValidNewPassword())) {
                member.updatePassword(encoder.encode(updateDto.getNewPassword()));
                member.updatePhone(member.getPhone());

            } else {
                throw new PasswordNotMatchException();
            }
        } else if (updateDto.getPhone() != null && updateDto.getNewPassword() == null && updateDto.getValidNewPassword() == null) {
            if (checkDuplicationPhoneUpdate(updateDto)) {
                throw new EXIST_MEMBER();
            }
            member.updatePhone(updateDto.getPhone());
        } else if (updateDto.getPhone() != null && updateDto.getNewPassword() != null && updateDto.getValidNewPassword() != null) {
            if (updateDto.getNewPassword().equals(updateDto.getValidNewPassword())) {
                if (checkDuplicationPhoneUpdate(updateDto)) {
                    throw new EXIST_MEMBER();
                }
                member.updatePassword(encoder.encode(updateDto.getNewPassword()));
                member.updatePhone(updateDto.getPhone());
            } else {
                throw new PasswordNotMatchException();
            }
        } else if (updateDto.getPhone() == null && updateDto.getNewPassword() == null && updateDto.getValidNewPassword() == null) {
           return MemberUpdateResponseDto.builder()
                   .password(encoder.encode(member.getPassword()))
                   .phone(member.getPhone())
                   .build();
        }
        Member savedMember = memberRepository.save(member);
        return MemberUpdateResponseDto.builder()
                .password(savedMember.getPassword())
                .phone(savedMember.getPhone())
                .build();
    }

    public MemberResponseDto getMemberInformation(String requestAccessTokenInHeader) {

        String token = jwtProvider.resolveToken(requestAccessTokenInHeader);
        String email = jwtProvider.getClaimsFromToken(token).get("email", String.class);
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        String refreshTokenIdRedis = redisService.getValues("RT : " + member.getEmail());

        if (jwtProvider.validate(requestAccessTokenInHeader)){
            throw new TokenExpirationException();
        }else {
            jwtProvider.reissue(requestAccessTokenInHeader, refreshTokenIdRedis);
        }

        return from(member);
    }

    @Transactional
    public MemberDeleteDto deleteMember(String requestAccessTokenInHeader) {  // 회원탈퇴

        String token = jwtProvider.resolveToken(requestAccessTokenInHeader);
        String email = jwtProvider.getClaimsFromToken(token).get("email", String.class);
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);

        if (jwtProvider.validate(requestAccessTokenInHeader)) {
            throw new TokenExpirationException();
        }
        String refreshTokenIdRedis = redisService.getValues("RT : " + member.getEmail());
        redisService.deleteValues(refreshTokenIdRedis);
        redisService.deleteValues("AT : " + member.getEmail());

//        memberRepository.delete(member);  // 회원정보 DB에서 삭제
        return member.deleteMember(email);
    }

    @Transactional
    public void logout(String requestAccessTokenInHeader) {

        String token = jwtProvider.resolveToken(requestAccessTokenInHeader);
        String email = jwtProvider.getClaimsFromToken(token).get("email", String.class);

        // Redis에 저장되어 있는 RT 삭제
        String refreshTokenInRedis = redisService.getValues("RT : " + email);
        if (refreshTokenInRedis != null) {
            redisService.deleteValues("RT : " + email);
        }

        // Redis에 로그아웃 처리한 AT를 블랙리스트로 저장, 만료될 떄까지 유효하지 않은 토큰으로 처리되어 해당하는 엑세스 토큰을 사용하려고 할 때 인증실패
        long expiration = jwtProvider.getTokenExpirationTime(token) - new Date().getTime();
        redisService.setValuesWithTimeout(token, "logout", expiration, TimeUnit.MICROSECONDS);
    }


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

    public boolean checkDuplicationPhoneUpdate(MemberUpdateDto updateDto) {
       return memberRepository.existsByPhone(updateDto.getPhone());
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
