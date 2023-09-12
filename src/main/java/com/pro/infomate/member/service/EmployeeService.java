package com.pro.infomate.member.service;


import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EmployeeService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    @Value("${image.image-url}")
    private String IMAGE_URL;

    public EmployeeService(MemberRepository memberRepository, ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    public MemberDTO selectMemberInfo(int memberCode) {                                         /* 직원 코드로 조회하기 */
        log.info("[EmployeeService] selectMemberInfo Start ==================");

        Member member = memberRepository.findById(memberCode).get();
        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
        memberDTO.setMemberPic(IMAGE_URL + memberDTO.getMemberPic());

        log.info("[EmployeeService] selectMemberInfo End ==================");

        return memberDTO;

    }
}
