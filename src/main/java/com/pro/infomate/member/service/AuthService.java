package com.pro.infomate.member.service;

import com.pro.infomate.exception.DuplicatedMemberEmailException;
import com.pro.infomate.exception.LoginFailException;
import com.pro.infomate.jwt.TokenProvider;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.dto.TokenDTO;
import com.pro.infomate.member.entity.AuthList;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.AuthListRepository;
import com.pro.infomate.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final ModelMapper modelMapper;
    private final AuthListRepository authListRepository;

    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, ModelMapper modelMapper, AuthListRepository authListRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.modelMapper = modelMapper;
        this.authListRepository = authListRepository;
    }


    public TokenDTO login(MemberDTO memberDTO) {

        log.info("[AuthService] login Start =======================================");
        log.info("[AuthService] {} ======================= ", memberDTO);

        /* 아이디 조회 */
        Member member = memberRepository.findByMemberId(memberDTO.getMemberId());

        log.info("[AuthService] member {} 조회 ======================= ", member);

        if (member == null) {
            throw new LoginFailException(memberDTO.getMemberId() + "를 찾을 수 없습니다.");
        }

        /* 패스워드 조회 */
        if (!passwordEncoder.matches(memberDTO.getMemberPassword(), member.getMemberPassword())){
            log.info("[AuthService] Password Match Fail!");
            throw new LoginFailException("잘못된 패스워드입니다.");
        }

        /* 토큰 조회 */
        TokenDTO tokenDTO = tokenProvider.generateTokenDTO(member);
        log.info("[AuthService] tokenDTO {} =======> ", tokenDTO);

        log.info("[AuthService] login End ==================================");

        return tokenDTO;
    }


    public Object signup(MemberDTO memberDTO){

        log.info("[AuthService] signup Start ==================================");
        log.info("[AuthService] memberDTO {} =======> ", memberDTO);

        if (memberRepository.findByMemberEmail(memberDTO.getMemberEmail()) != null){
            log.info("[AuthService] 이메일이 종복됩니다.");
            throw new DuplicatedMemberEmailException("이메일이 중복됩니다.");
        }

        Member registMember = modelMapper.map(memberDTO, Member.class);

        registMember.setMemberPassword(passwordEncoder.encode(registMember.getMemberPassword()));
        Member result1 = memberRepository.save(registMember);
        log.info("[AuthService] result1 ================== {} ", result1);

        int MaxMemberCode = memberRepository.maxMemberCode();

        AuthList registAuth = new AuthList(MaxMemberCode, 2);

        AuthList result2 = authListRepository.save(registAuth);

        log.info("[AuthService] MemberInsert Result {}",
                (result1 != null && result2 != null)? "회원 가입 성공" : "회원 가입 실패");

        log.info("[AuthService] signup End ==================================");

        return memberDTO;
    }
}
