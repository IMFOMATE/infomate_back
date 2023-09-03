package com.pro.infomate.member.controller;

import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.member.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/emp")
@RestController
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/info/{memberCode}")
    public ResponseEntity<ResponseDTO> selectMemberInfo(@PathVariable int memberCode){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "직원 조회 성공", employeeService.selectMemberInfo(memberCode)));
    }




}
