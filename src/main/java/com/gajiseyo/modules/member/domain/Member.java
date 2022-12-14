package com.gajiseyo.modules.member.domain;


import com.gajiseyo.modules.common.BaseTimeEntity;
import com.gajiseyo.modules.file.domain.ProgFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "file_id", referencedColumnName = "file_id"),
            @JoinColumn(name = "file_seq", referencedColumnName = "seq"),
    })
    private ProgFile picture;

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

    public void changePicture(ProgFile picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member member = (Member) o;
        return id != null && Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
