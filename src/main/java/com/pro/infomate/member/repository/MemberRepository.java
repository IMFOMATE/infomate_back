package com.pro.infomate.member.repository;

import com.pro.infomate.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

  @EntityGraph(attributePaths = {"department", "rank"})
  Member findByMemberId(long memberId);

  List<Member> findByMemberCodeIn(List<Long> ids);

  Member findByMemberCode(Long memberCode);
}
