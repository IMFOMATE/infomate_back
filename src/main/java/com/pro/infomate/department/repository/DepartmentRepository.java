package com.pro.infomate.department.repository;

import com.pro.infomate.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {



    List<Department> findByDeptNameContaining(String searchKeyword);

    List<Department> findByDeptName(String deptName);

//    List<Department> findByCode(int deptCode);

//    List<Department> findByDeptName(String search);


    List<Department> findAllByDeptCodeAfter(int deptCode);

//    @Query("SELECT COALESCE(MAX(deptOrder), 0) FROM Department")
    @Query("SELECT MAX(d.deptOrder) FROM Department d")
    Long findMaxId();

    @Query("SELECT d from Department d where d.deptCode = :deptCode")
    Optional<Department> findById(@Param("deptCode") int deptCode);

}
