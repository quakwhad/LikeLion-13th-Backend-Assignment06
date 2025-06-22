package com.likelion.likelionjwt.member.domain.repository;

import com.likelion.likelionjwt.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 반환값이 유일할 때 사용, 이메일은 유일하기 때문에 사용함
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
}
