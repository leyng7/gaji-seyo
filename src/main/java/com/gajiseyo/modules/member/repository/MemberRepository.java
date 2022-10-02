package com.gajiseyo.modules.member.repository;

import com.gajiseyo.modules.member.domain.Member;
import com.gajiseyo.modules.member.repository.custom.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findBySnsKey(String snsKey);

}
