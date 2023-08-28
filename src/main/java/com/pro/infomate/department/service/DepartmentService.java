package com.pro.infomate.department.service;

import com.pro.infomate.department.dto.TreeViewResponse;
import com.pro.infomate.department.entity.Department;
import com.pro.infomate.department.repository.DepartmentRepository;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

  private final DepartmentRepository departmentRepository;

  private final MemberRepository memberRepository;

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
                      .build())
              .build();
      memberNodes.add(memberNode);
    }
    return memberNodes;
  }

}
