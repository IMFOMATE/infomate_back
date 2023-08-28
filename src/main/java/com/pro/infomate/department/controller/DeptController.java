package com.pro.infomate.department.controller;

import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DeptController {

  private final DepartmentService departmentService;

  @GetMapping("/treeview")
  public ResponseEntity<ResponseDTO> deptTreeView(){


    return ResponseEntity.ok()
            .body(ResponseDTO.builder()
                    .status(HttpStatus.OK)
                    .message("success")
                    .data(departmentService.deptTreeViewList())
                    .build());
  }
}
