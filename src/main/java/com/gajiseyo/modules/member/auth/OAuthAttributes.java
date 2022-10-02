package com.gajiseyo.modules.member.auth;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private final Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private final String nameAttributeKey;
    private final String snsKey;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String snsKey) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.snsKey = snsKey;
    }

    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
        return ofNaver("id", attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .snsKey((String) response.get("id"))
                .build();
    }

}
