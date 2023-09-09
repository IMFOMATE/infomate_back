package com.pro.infomate.department.service;

import com.pro.infomate.common.Criteria;
import com.pro.infomate.department.dto.DepartmentDTO;
import com.pro.infomate.department.dto.DepartmentListResponse;
import com.pro.infomate.department.dto.DeptListResponse;
import com.pro.infomate.department.dto.TreeViewResponse;
import com.pro.infomate.department.entity.Department;
import com.pro.infomate.department.repository.DepartmentRepository;
import com.pro.infomate.exception.NotFindDataException;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
                            .deptCode(member.getDepartment().getDeptCode())
                            .build()
                            ).build();
      memberList.add(deptList);
    }
    log.info("{}=============",memberList);

    return memberList;


  }


  //////////////////////////////  페이징

  public int selectDeptTotal() {      // 멤버 카운트

    log.info("[DepartmentService] selectDeptTotal Start ========================== ");

    List<Member> memberList = memberRepository.findAll();
    log.info("[DepartmentService] departmentList.size : {} ", memberList.size());
    log.info("[DepartmentService] selectDeptTotal End ========================== ");

    return memberList.size();

  }



  public Object selectDeptListWithPaging(Criteria cri) {
    log.info("[DepartmentService] selectDeptListWithPaging Start ========================== ");
    int index = cri.getPageNum() -1;
    int count = cri.getAmount();
    Pageable paging = PageRequest.of(index, count, Sort.by("memberCode").descending());

    Page<Member> result = memberRepository.findAll(paging);
    List<Member> memberList =(List<Member>) result.getContent();

    // 이미지 관련 꺼내오기


    log.info("[DepartmentService] selectDeptListWithPaging End ========================== ");
    return memberList.stream().map(member -> modelMapper.map(member, MemberDTO.class)).collect(Collectors.toList());
  }

  public List<DepartmentDTO> selectSearchDeptList(String search) {
    log.info("[DepartmentService] selectSearchDeptList Start ========================== ");
    log.info("[DepartmentService] Search {} ", search);

    List<Department> departmentListWithSearchValue = departmentRepository.findByDeptNameContaining(search);

    List<DepartmentDTO> departmentDTOList = departmentListWithSearchValue.stream()
            .map(department -> modelMapper.map(department, DepartmentDTO.class)).collect(Collectors.toList());

    for(int i = 0; i < departmentDTOList.size(); i++){
      departmentDTOList.get(i).setDeptName(departmentDTOList.get(i).getDeptName());
    }
    log.info("[DepartmentService] selectSearchDeptList End ========================== ");

    return departmentDTOList;
  }


    public List<DepartmentDTO> selectDeptAll() {      // 부서 전체 조회

      List<Department> deptListAll = departmentRepository.findAll();



      return deptListAll.stream().map(department -> modelMapper.map(department, DepartmentDTO.class)).collect(Collectors.toList());
    }

  public List<MemberDTO> selectEmpAll() {         // 멤버 전체조회
    List<Member> employeeAll = memberRepository.findAll();

    return employeeAll.stream().map(member -> modelMapper.map(member, MemberDTO.class)).collect(Collectors.toList());
  }

  public List<DepartmentListResponse> selectDeptPart(Department dept) {

    List<Department> deptPartAll = departmentRepository.findAll();
    List<DepartmentListResponse> result = new ArrayList<>();

    for(Member member : dept.getMembers() ){

      DepartmentListResponse deptList = DepartmentListResponse.builder()
              .deptCode(member.getDepartment().getDeptCode())
              .deptName(member.getDepartment().getDeptName())
//              .memberName(member.getMemberName())
              .build();
      result.add(deptList);
    }

    return result;
  }


  public List<DepartmentListResponse> selectPartList() {

    List<Department> partList = departmentRepository.findAll();
    List<DepartmentListResponse> result = new ArrayList<>();

    return null;
  }


  public Page<MemberDTO> openEmpList( Pageable pageable, String findSearch ) {

//    Page<Member> empList = memberRepository.findByMemberCode(memberCode, pageable);

    Page<Member> empList = null;
    if(findSearch.equals("all")){
      empList = memberRepository.findAll(pageable);
    } else {
      empList = memberRepository.findByMemberNameContaining(findSearch, pageable);
    }

//    if(empList.getContent().size() == 0) throw new NotFindDataException("조회할 데이터가 없습니다.");
    log.info("[DepartmentService] openEmpList =========== findSearch {}", findSearch);

    return empList.map(member-> modelMapper.map(member, MemberDTO.class));

  }

  @Transactional
  public String updateDept(DepartmentDTO departmentDTO) {
    log.info("[DepartmentService] updateDept Start ============================= ");
    log.info("[DepartmentService] departmentDTO : {} " , departmentDTO );

//    int result = 0;

    Department department = departmentRepository.findById(departmentDTO.getDeptCode()).get();
    log.info("[DepartmentService] department : {} ", department);

    department.setDeptName(departmentDTO.getDeptName());

  return null;
  }
}












