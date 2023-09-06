package com.pro.infomate.member.service;

import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.RegistRepository;
import com.pro.infomate.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Slf4j
public class RegistService {

    private final RegistRepository registRepository;
    private final ModelMapper modelMapper;

    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    @Value("${image.image-url}")
    private String IMAGE_URL;

    public RegistService(RegistRepository registRepository, ModelMapper modelMapper) {
        this.registRepository = registRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public String registMember(MemberDTO memberDTO, MultipartFile memberImage){
        log.info("[RegistService] registMember Start ===================");
        log.info("[RegistService] productDTO : " + memberDTO);

        String imageName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;
        int result = 0;

        try{
            replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, memberImage);

            memberDTO.setMemberPic(replaceFileName);

            log.info("[RegistService] insert Image Name : ", replaceFileName);

            // 저장을 위해서 일반 DTO객체를 Entity객체로 변경
            Member registMember = modelMapper.map(memberDTO, Member.class);

            registRepository.save(registMember);

            result = 1;
        } catch (Exception e){
            System.out.println("check");
            FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            throw new RuntimeException(e);
        }


        log.info("[RegistService] registMember End ===================");
        return (result > 0)? "회원 등록 성공": "회원 등록 실패";
    }
}
