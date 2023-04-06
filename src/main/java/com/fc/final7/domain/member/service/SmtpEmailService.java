package com.fc.final7.domain.member.service;

import com.fc.final7.domain.member.dto.MailDto;
import com.fc.final7.domain.member.entity.Member;
import com.fc.final7.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SmtpEmailService {

    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;


    public MailDto createMail(String email, String phone)  { // 메일을 받는 대상의 이메일과 번호
        String uuid = "";
        Member member = memberRepository.findByEmailAndPhone(email, phone).orElseThrow(EntityNotFoundException::new);

        if (member != null){
             uuid = resetPassword(member);
        }

        MailDto mailDto = new MailDto();
        mailDto.setEmail(member.getEmail());
        mailDto.setTitle("goTogether 임시비밀번호 안내 이메일 입니다.");
        mailDto.setMessage("goTogether 임시비밀번호 안내 이메일 입니다. 회원님의 임시 비밀번호는 "+ uuid + "입니다. 로그인 후 반드시 비밀번호를 변경해주세요");
        return mailDto;
    }

    @Transactional
    public String resetPassword(Member member){
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        member.updatePassword(encoder.encode(uuid));
        memberRepository.save(member);
        return uuid;
    }

    public void sendTemporaryPasswordMail(MailDto mailDto) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false); // false로 설정하면 단순 text메시지로 전달

        messageHelper.setTo(mailDto.getEmail());
        messageHelper.setSubject(mailDto.getTitle());
        messageHelper.setText(mailDto.getMessage());
        messageHelper.setFrom("chas369@naver.com");

        javaMailSender.send(mimeMessage);
    }
}
