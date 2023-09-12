package com.pro.infomate.department.controller;

import com.pro.infomate.common.Criteria;
import com.pro.infomate.common.PageDTO;
import com.pro.infomate.common.PagingResponseDTO;
import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.department.dto.DepartmentDTO;
import com.pro.infomate.department.service.DepartmentService;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.repository.MemberRepository;
import com.pro.infomate.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
@Slf4j
public class DeptController {

  private final DepartmentService departmentService;

  private final MemberService memberService;

  //////////////////////////* 직원 조회 *//////////////////////
  @GetMapping("/emp/list")
  public ResponseEntity<ResponseDTO> deptList() {

    return ResponseEntity.ok()
            .body(ResponseDTO.builder().status(HttpStatus.OK).message("조회성공").data(departmentService.selectDeptList()).build());

  }



  //////////////////* 직원 전체 조회 */
  @GetMapping("/emp/listall")
  public ResponseEntity<ResponseDTO> employeeList(){

    return ResponseEntity.ok()
            .body(new ResponseDTO(HttpStatus.OK, "모든 부서 리스트 조회 성공",
                    departmentService.selectEmpAll()));

  }



  //////////////////////////* 부서 조회 *//////////////////////

  @GetMapping("/dept/list")
  public ResponseEntity<ResponseDTO> selectDeptAll(){

    return ResponseEntity.ok()
            .body(new ResponseDTO(HttpStatus.OK, "모든 부서 리스트 조회 성공",
                    departmentService.selectDeptAll()));
  }




  @GetMapping("/treeview")
  public ResponseEntity<ResponseDTO> deptTreeView() {


    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(departmentService.deptTreeViewList())
                    .build());
  }







  // 페이징
  @GetMapping("/openEmpList")
  public ResponseEntity<PagingResponseDTO> findOpenEmpList(@PageableDefault(size = 10, sort = "memberCode", direction = Sort.Direction.DESC) Pageable pageable, MemberDTO member,
                                                           @RequestParam(name = "s", defaultValue = "all") String findSearch){

    int memberCode = member.getMemberCode();

    log.info("[DeptController] (findOpenEmpLIst) pageable : {}", pageable);

    pageable = PageRequest.of(
            pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() -1 ,   // 페이지넘버
            pageable.getPageSize(),                                               // 페이지 사이즈
            pageable.getSort());                                                  // 페이지당 갯수

    Page<MemberDTO> mamberDTOPage = departmentService.openEmpList(pageable, findSearch);

    log.info("[DeptController] (findOpenEmpList) memberDTOPage.getSize() : {}", mamberDTOPage.getSize());
    log.info("[DeptController] (findOpenEmpList) memberDTOPage.getContent() : {}", mamberDTOPage.getContent());

    PageDTO pageDTO = new PageDTO(new Criteria(pageable.getPageNumber(), pageable.getPageSize()),mamberDTOPage.getTotalPages());
    log.info("[DeptController] (findOpenEmpList) pageDTO : {}", pageDTO);
    log.info("[DeptController] (findOpenEmpList) findSearch : {}", findSearch);
    log.info("[DeptController] (findOpenEmpList) memberDTOPage : {}", mamberDTOPage);

    return ResponseEntity.ok()
            .body(PagingResponseDTO.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("succes")
                    .pageInfo(pageDTO)
                    .data(mamberDTOPage.getContent())
                    .build());

  }

  @GetMapping("/participantList")
  public ResponseEntity<ResponseDTO> participantList(){

    return ResponseEntity.ok().body(
            ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .data(departmentService.participantList())
                    .build()
    );
  }



  @PostMapping("/regist")
  public ResponseEntity<ResponseDTO> saveByDepartment(@RequestBody DepartmentDTO departmentDTO){

    return ResponseEntity.ok()
            .body(new ResponseDTO(HttpStatus.OK, "부서 입력 성공", departmentService.saveByDepartment(departmentDTO)));


  }


  //   조직도 수정하기
  @PutMapping("/save")
  public ResponseEntity<ResponseDTO> updateDept(@RequestBody DepartmentDTO departmentDTO){

    log.info("[DeptController] (updateDept) departmentDTO : {}", departmentDTO);

    departmentService.updateDept(departmentDTO);

    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("정상 수정")
                    .build());

  }


//  @DeleteMapping("/delete/{deptCode}")
//  public ResponseEntity<ResponseDTO> deleteDept(@PathVariable int deptCode){
//
//    log.info("[DepartmentController] ======> deptCode: {}" , deptCode);
//
//    departmentService.deletDept(deptCode);
//
//    return ResponseEntity.ok()
//            .body(ResponseDTO.builder()
//                    .status(HttpStatus.OK)
//                    .message("삭제완료")
//                    .build());
//
//
//  }



}





