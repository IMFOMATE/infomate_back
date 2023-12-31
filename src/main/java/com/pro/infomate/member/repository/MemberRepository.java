package com.pro.infomate.member.repository;

import com.pro.infomate.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> {



    //  @EntityGraph(attributePaths = {"department", "rank"})
  Member findByMemberId(String memberId);

  List<Member> findByMemberCodeIn(List<Integer> ids);

  Member findByMemberCode(int memberCode);

  Page<Member> findByMemberCode(int i, Pageable paging);
    @Query("SELECT MAX(a.memberCode) FROM Member a")
    int maxMemberCode();

  Member findByMemberName(Object o);

    Page<Member> findByMemberNameContaining(String findSearch, Pageable pageable);

//  boolean findByMemberEmail(String memberEmail);
}
