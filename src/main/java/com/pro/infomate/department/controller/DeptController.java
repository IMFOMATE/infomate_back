package com.pro.infomate.department.controller;

import com.pro.infomate.common.Criteria;
import com.pro.infomate.common.PageDTO;
import com.pro.infomate.common.PagingResponseDTO;
import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.department.service.DepartmentService;
import com.pro.infomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
@Slf4j
public class DeptController {

  private final DepartmentService departmentService;


  //////////////////////////* 직원 조회 *//////////////////////
  @GetMapping("/emp/list")
  public ResponseEntity<ResponseDTO> deptList(){

    return ResponseEntity.ok()
            .body(ResponseDTO.builder().status(HttpStatus.OK).message("조회성공").data(departmentService.selectDeptList()).build());

  }

//  @GetMapping("/dept/part")
//  public ResponseEntity<ResponseDTO> deptPartList(){
//
//    return ResponseEntity.ok()
//            .body(new ResponseDTO(HttpStatus.OK,"조직도 조회 성공", departmentService.selectPartList()));
//  }


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
  public ResponseEntity<ResponseDTO> deptTreeView(){


    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(departmentService.deptTreeViewList())
                    .build());
  }


//  public ResponseEntity<ResponseDTO> selectSearchDeptList(
//          @RequestParam(name = "s", defaultValue = "all") String search){
//
//    return ResponseEntity.ok()
//            .body(new ResponseDTO(HttpStatus.OK, "검색 성공", departmentService.selectDept(search)));
//  }


  @GetMapping("/participantList")
  public ResponseEntity<ResponseDTO> participantList(){

    return ResponseEntity.ok().body(
            ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .data(departmentService.participantList())
                    .build()
    );
  }



  // 페이지

  @GetMapping("/dept")
  public ResponseEntity<ResponseDTO> selectDeptListWithPaging(
          @RequestParam(name = "offset", defaultValue = "1") String offset ){

    log.info("[DeptController] selectDeptListWithPaging Start ========== ");
    log.info("[DeptController] selectDeptListWithPaging offset : {} ", offset);

    int total = departmentService.selectDeptTotal();

    Criteria cri = new Criteria(Integer.parseInt(offset), 10);

    /* 페이지에 맞는 부서 */
    PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

    pagingResponseDTO.setData(departmentService.selectDeptListWithPaging(cri));

    pagingResponseDTO.setPageInfo(new PageDTO(cri, total));
    log.info("[DeptController] selectDeptListWithPaging End ========== ");

    return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
  }


  @GetMapping("/dept/search")
  public ResponseEntity<ResponseDTO> selectSearchDeptList(@RequestParam(name = "s", defaultValue = "all") String search){

    return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "검색 성공",departmentService.selectSearchDeptList(search)));
  }



}





