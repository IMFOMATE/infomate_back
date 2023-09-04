package com.pro.infomate.department.controller;

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


  @GetMapping("/emp/list")
  public ResponseEntity<ResponseDTO> deptList(){

    return ResponseEntity.ok()
            .body(ResponseDTO.builder().status(HttpStatus.OK).message("조회성공").data(departmentService.selectDeptList()).build());

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


  public ResponseEntity<ResponseDTO> selectSearchDeptList(
          @RequestParam(name = "s", defaultValue = "all") String search){

    return ResponseEntity.ok()
            .body(new ResponseDTO(HttpStatus.OK, "검색 성공", departmentService.selectDept(search)));
  }




}
