package com.pro.infomate.department.service;

import com.pro.infomate.department.dto.TreeViewResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DepartmentServiceTest {

  @Autowired DepartmentService departmentService;


  @Test
  @DisplayName("treeview")
  void 트리뷰resp테스트() {

    // When
    List<TreeViewResponse> result = departmentService.deptTreeViewList();

    result.forEach(r -> System.out.println("r = " + r));

    // Then

  }



}