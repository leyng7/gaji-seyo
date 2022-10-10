package com.gajiseyo.modules.common;


import com.gajiseyo.modules.member.auth.MemberOauthAdapter;
import com.gajiseyo.modules.member.domain.Member;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberAwareAudit implements AuditorAware<Member> {

    @Override
    public Optional<Member> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof MemberOauthAdapter) {
            MemberOauthAdapter adapter = (MemberOauthAdapter) principal;
            return Optional.ofNullable(adapter.getMember());
        }

        return Optional.empty();
    }
}
