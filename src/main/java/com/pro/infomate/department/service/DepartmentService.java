package com.pro.infomate.department.service;

import com.pro.infomate.department.dto.DepartmentDTO;
import com.pro.infomate.department.dto.DeptListResponse;
import com.pro.infomate.department.dto.TreeViewResponse;
import com.pro.infomate.department.entity.Department;
import com.pro.infomate.department.repository.DepartmentRepository;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DepartmentService {

  private final DepartmentRepository departmentRepository;

  private final MemberRepository memberRepository;

  private final ModelMapper modelMapper;

  public List<DeptListResponse> selectDeptList() {
    log.info("[DepartmentService] selectDeptList Start ====================== ");
    List<Department> deptlist= departmentRepository.findAll();
    List<DeptListResponse> result = new ArrayList<>();
    int count = deptlist.size();

    for(Department dept : deptlist) {
      result.addAll(createDeptList(dept, count));
      count += dept.getMembers().size();
    }
    return result;

  }


  public List<TreeViewResponse> deptTreeViewList() {
    List<Department> allDept = departmentRepository.findAll();
    List<TreeViewResponse> result = new ArrayList<>();

    int count = allDept.size(); // 부서의 갯수에서부터 count 시작

    for (Department dept : allDept) {
      result.add(createDepartmentNode(dept));
      result.addAll(createMemberNodes(dept, count));
      count += dept.getMembers().size(); // Increment the counter by the number of members
    }
    return result;
  }

  //부서 매핑
  private TreeViewResponse createDepartmentNode(Department dept) {
    return TreeViewResponse.builder()
            .id(dept.getDeptCode())
            .parent(0)
            .droppable(true)
            .text(dept.getDeptName())
            .build();
  }

  //부서 멤버 매핑
  private List<TreeViewResponse> createMemberNodes(Department dept, int count) {
    List<TreeViewResponse> memberNodes = new ArrayList<>();

    for (Member member : dept.getMembers()) {
      TreeViewResponse memberNode = TreeViewResponse.builder()
              .id(++count)
              .parent(dept.getDeptCode())
              .droppable(false)
              .text(member.getMemberName())
              .data(TreeViewResponse.TreeDTO.builder()
                      .fileType("person")
                      .rank(member.getRank().getRankName())
                      .profile(member.getMemberPic())
                      .memberCode(member.getMemberCode())
                      .build())
              .build();
      memberNodes.add(memberNode);
    }
    return memberNodes;
  }


  private List<DeptListResponse> createDeptList(Department dept, int count){      // 부서 리스트 넣어주기
    List<Department> all = departmentRepository.findAll();
    List<DeptListResponse> memberList = new ArrayList<>();

    for (Member member : dept.getMembers()){
      DeptListResponse deptList = DeptListResponse.builder()
                      .id(++count)
                      .data(DeptListResponse.DepartDTO.builder()
                            .empName(member.getMemberName())
                            .empNum(member.getMemberId())
                            .deptName(member.getDepartment().getDeptName())
                            .rankName(member.getRank().getRankName())
                            .empCode(member.getMemberCode())
                            .rank(member.getRank().getRankPlace())
                            .build()
                            ).build();
      memberList.add(deptList);
    }
    log.info("{}=============",memberList);

    return memberList;

  }



}
