package state.member.application.processor;

import org.springframework.stereotype.Component;
import state.member.domain.entity.Member;
import state.member.domain.repository.MemberRepository;

import java.util.Optional;

@Component
public class MemberFindByUsernameProcessor {
    private final MemberRepository memberRepository;

    public MemberFindByUsernameProcessor(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> execute(String username) {
        return memberRepository.findByUsername(username);
    }
}
