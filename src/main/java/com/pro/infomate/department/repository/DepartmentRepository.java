package com.pro.infomate.department.repository;

import com.pro.infomate.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {


    List<Department> findByDeptNameContaining(String searchKeyword);

    List<Department> findByDeptName(String deptName);

}
