package com.dgmoonlabs.psythinktank.domain.member.repository;

import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndMemberId(String email, String memberId);

    Optional<Member> findByMemberId(String memberId);

    void deleteByMemberId(String memberId);
}
