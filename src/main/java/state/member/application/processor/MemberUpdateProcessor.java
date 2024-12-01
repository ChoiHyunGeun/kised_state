package state.member.application.processor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import state.member.application.command.MemberUpdateCommand;
import state.member.domain.entity.Member;
import state.member.domain.exception.MemberNotFoundException;
import state.member.domain.repository.MemberRepository;

@Component
public class MemberUpdateProcessor {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberUpdateProcessor(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(MemberUpdateCommand memberUpdateCommand) {
        if (!memberRepository.existsById(memberUpdateCommand.getSeq())) {
            throw new MemberNotFoundException();
        }
        Member member = memberRepository.getReferenceById(memberUpdateCommand.getSeq());
        //비밀번호 암호화
        memberUpdateCommand.setPassword(passwordEncoder.encode(memberUpdateCommand.getPassword()));
        //권한 수정
        member.setPassword(memberUpdateCommand.getPassword());
        member.setUserRole(memberUpdateCommand.getUserRole());
    }
}