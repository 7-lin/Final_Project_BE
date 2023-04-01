package com.fc.final7.domain.member.service;

import com.fc.final7.domain.jwt.JwtProvider;
import com.fc.final7.domain.jwt.dto.TokenDto;
import com.fc.final7.domain.member.dto.MemberLoginDto;
import com.fc.final7.domain.member.dto.MemberResponseDto;
import com.fc.final7.domain.member.dto.SignUpRequestDto;
import com.fc.final7.domain.member.entity.Member;
import com.fc.final7.domain.member.repository.MemberRepository;
import com.fc.final7.global.exception.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public MemberResponseDto signUp(SignUpRequestDto requestDto) throws Exception {
        if (checkDuplicationEmail(requestDto)) {
            throw new DuplicateEmailException();
        }

        Member member = Member.createMember(requestDto, encoder);
        Member savedMember = memberRepository.save(member);
        return from(savedMember);
    }

    @Transactional
    public MemberResponseDto login(MemberLoginDto loginDto) {
        TokenDto tokenDto = null;
        Member member = memberRepository.findByEmail(loginDto.getEmail()).orElseThrow(EntityNotFoundException::new);
        if (encoder.matches(loginDto.getPassword(), member.getPassword())) {
            tokenDto = jwtProvider.issue(member);
        }
        return MemberResponseDto.builder()
                .tokenDto(tokenDto)
                .name(member.getName())
                .email(member.getEmail())
                .age(member.getAge())
                .gender(member.getGender())
                .build();
    }

    public MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .gender(member.getGender())
                .age(member.getAge())
                .build();
    }

    public boolean checkDuplicationEmail(SignUpRequestDto requestDto) {
        return memberRepository.existsByEmail(requestDto.getEmail());
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
