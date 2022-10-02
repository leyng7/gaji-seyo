package com.gajiseyo.modules.member.domain;


import com.gajiseyo.modules.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "TB_MEMBER")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements Serializable {

    private static final long serialVersionUID = 2438826685859396244L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String snsKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // 닉네임
    private String nickname;

    // 프로필 사진

    // 매너 온도

    public String getRoleKey() {
        return this.role.getKey();
    }

    public Member(String snsKey) {
        this.snsKey = snsKey;
        this.role = Role.USER;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

}
