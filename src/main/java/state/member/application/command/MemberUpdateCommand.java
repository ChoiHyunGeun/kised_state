package state.member.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberUpdateCommand {
    int seq;
    String password;
    String userRole;
}
