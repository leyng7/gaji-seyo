package com.gajiseyo.modules.member.auth;

import com.gajiseyo.modules.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class MemberOauthAdapter extends DefaultOAuth2User {

    private static final long serialVersionUID = -1838000964621879894L;

    private final Member member;
    
    public MemberOauthAdapter(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, Member member) {
        super(authorities, attributes, nameAttributeKey);
        this.member = member;
    }

}
