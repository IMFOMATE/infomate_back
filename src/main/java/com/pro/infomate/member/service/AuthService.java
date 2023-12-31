package com.pro.infomate.member.service;

import com.pro.infomate.calendar.service.CalendarService;
import com.pro.infomate.department.entity.Department;
import com.pro.infomate.department.repository.DepartmentRepository;
import com.pro.infomate.exception.LoginFailException;
import com.pro.infomate.jwt.TokenProvider;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.dto.TokenDTO;
import com.pro.infomate.member.entity.AuthList;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.entity.Rank;
import com.pro.infomate.member.repository.AuthListRepository;
import com.pro.infomate.member.repository.MemberRepository;
import com.pro.infomate.member.repository.RankRepository;
import com.pro.infomate.util.FileUploadUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    private final CalendarService calendarService;
    private final ModelMapper modelMapper;
    private final AuthListRepository authListRepository;

    private final DepartmentRepository departmentRepository;
    private final RankRepository rankRepository;

    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    @Value("${image.image-url}")
    private String IMAGE_URL;

//    public AuthService(MemberRepository memberRepository,
//                       PasswordEncoder passwordEncoder,
//                       TokenProvider tokenProvider,
//                       CalendarService calendarService, ModelMapper modelMapper,
//                       AuthListRepository authListRepository) {
//
//        this.memberRepository = memberRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.tokenProvider = tokenProvider;
//        this.calendarService = calendarService;
//        this.modelMapper = modelMapper;
//        this.authListRepository = authListRepository;
//    }


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

        /* 프로필 사진 조회 */
        tokenDTO.setMemberPic(IMAGE_URL + tokenDTO.getMemberPic());
        tokenDTO.setMemberPicDefault(IMAGE_URL + tokenDTO.getMemberPicDefault());

        log.info("[AuthService] login End ==================================");

        return tokenDTO;
    }

    @Transactional
    public Object regist(MemberDTO memberDTO, MultipartFile memberImage) {

        log.info("[AuthService] registMember Start ==================================");
        log.info("[AuthService] memberDTO {} =======> ", memberDTO);

        String imageName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;
        Department deparment = departmentRepository.findById(memberDTO.getDeptCode()).orElseThrow(() -> new RuntimeException("부서가 없습니다"));
        Rank rank = rankRepository.findById((long) memberDTO.getRankCode()).orElseThrow(() -> new RuntimeException("부서가 없습니다"));

        try {
        // 파일 업로드 및 저장
        replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, memberImage);
        String originalFileName = memberImage.getOriginalFilename();
        log.info("[AuthService] insert Image Name : ", replaceFileName);

        Member registMember = modelMapper.map(memberDTO, Member.class);
        registMember.setMemberPic(replaceFileName);
        registMember.setMemberPicDefault(originalFileName);
        registMember.setDepartment(deparment);
        registMember.setRank(rank);

        // 비밀번호 암호화
        registMember.setMemberPassword(passwordEncoder.encode(registMember.getMemberPassword()));

        // 회원 정보 저장
        Member result1 = memberRepository.save(registMember);
        log.info("[AuthService] result1 ================== {} ", result1);


            System.out.println("result1  = " + result1.getMemberCode() );

        // 회원 코드 가져오기
//        int MaxMemberCode = memberRepository.maxMemberCode();

        // 권한 정보 저장
        AuthList registAuth = new AuthList(result1.getMemberCode(), 2);
        AuthList result2 = authListRepository.save(registAuth);

        // 계정생성시 기본 캘린더 생성 서비스
        calendarService.saveFirstCalendarRegist(registMember.getMemberCode());

        log.info("[AuthService] MemberInsert Result {}",
                (result1 != null && result2 != null) ? "회원 가입 성공" : "회원 가입 실패");

        log.info("[AuthService] signup End ==================================");

        return memberDTO;
    } catch (Exception e) {
        log.error("[AuthService] 회원 등록 중 오류 발생: {}", e.getMessage());
        throw new RuntimeException("회원 등록 중 오류 발생");
    }
    }
}
