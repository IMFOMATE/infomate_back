package com.pro.infomate.approval.service.visitor;

import com.pro.infomate.approval.dto.response.*;
import com.pro.infomate.approval.entity.*;
import com.pro.infomate.approval.dto.response.VacationResponse;
import com.pro.infomate.member.entity.Member;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DocumentToDTOVisitor implements DocumentVisitor<DocumentDetailResponse> {
  private MemberResponse mapMemberResponse(Member member) {
    return MemberResponse.builder()
            .memberCode(member.getMemberCode())
            .memberName(member.getMemberName())
            .deptName(member.getDepartment().getDeptName())
            .build();
  }

  private List<ApprovalResponse> mapApprovalList(List<Approval> approvals) {
    return approvals.stream()
            .map(approval -> {
              Member approvalMember = approval.getMember();
              return ApprovalResponse.builder()
                      .memberCode(approvalMember.getMemberCode())
                      .rankName(approvalMember.getRank().getRankName())
                      .profile(approvalMember.getMemberPic())
                      .approvalStatus(approval.getApprovalStatus().toString())
                      .memberName(approvalMember.getMemberName())
                      .comment(approval.getComment())
                      .approvalDate(approval.getApprovalDate())
                      .order(approval.getOrder())
                      .build();
            })
            .collect(Collectors.toList());
  }

  private List<DocFileResponse> mapFileList(List<DocumentFile> files) {
    return files.stream()
            .map(file -> DocFileResponse.builder()
                    .fileCode(file.getFileCode())
                    .fileName(file.getFileName())
                    .fileType(file.getFileType())
                    .fileSize(file.getFileSize())
                    .build())
            .collect(Collectors.toList());
  }

  private List<RefMemberResponse> mapRefList(List<DocRef> docRefs) {
    return docRefs.stream()
            .map(docRef -> {
              Member result = docRef.getMember();
              return RefMemberResponse.builder()
                      .memberName(result.getMemberName())
                      .profile(result.getMemberPic())
                      .rankName(result.getRank().getRankName())
                      .build();
            })
            .collect(Collectors.toList());
  }
  @Override
  public DocumentDetailResponse visit(Vacation vacation) {
    Member member = vacation.getMember();

    List<ApprovalResponse> approvalList = mapApprovalList(vacation.getApprovalList());
    List<DocFileResponse> fileList = mapFileList(vacation.getFileList());
    List<RefMemberResponse> refList = mapRefList(vacation.getRefList());

    return VacationResponse.builder()
            .id(vacation.getId())
            .title(vacation.getTitle())
            .createdDate(vacation.getCreatedDate())
            .documentStatus(vacation.getDocumentStatus())
            .emergency(vacation.getEmergency())
            .member(mapMemberResponse(member))
            .content(vacation.getContent())
            .fileList(fileList)
            .approvalList(approvalList)
            .refList(refList)
            .documentKind(vacation.getDocumentKind())
            .startDate(vacation.getStartDate())
            .endDate(vacation.getEndDate())
            .sort(vacation.getSort())
            .build();
  }

  @Override
  public DocumentDetailResponse visit(Payment payment) {
    Member member = payment.getMember();

    List<ApprovalResponse> approvalList = mapApprovalList(payment.getApprovalList());
    List<DocFileResponse> fileList = mapFileList(payment.getFileList());
    List<RefMemberResponse> refList = mapRefList(payment.getRefList());


    List<PaymentListResponse> paymentList = payment.getPaymentList().stream()
            .map(payments -> PaymentListResponse.builder()
                    .paymentDate(payments.getPaymentDate())
                    .paymentContent(payments.getPaymentContent())
                    .paymentPrice(payments.getPaymentPrice())
                    .paymentSort(payments.getPaymentSort())
                    .remarks(payments.getRemarks())
                    .build()
            ).collect(Collectors.toList());

    return PaymentResponse.builder()
            .id(payment.getId())
            .title(payment.getTitle())
            .createdDate(payment.getCreatedDate())
            .emergency(payment.getEmergency())
            .documentStatus(payment.getDocumentStatus())
            .member(mapMemberResponse(member))
            .content(payment.getContent())
            .fileList(fileList)
            .approvalList(approvalList)
            .documentKind(payment.getDocumentKind())
            .paymentList(paymentList)
            .refList(refList)
            .build();
  }

  @Override
  public DocumentDetailResponse visit(Draft draft) {
    Member member = draft.getMember();

    List<ApprovalResponse> approvalList = mapApprovalList(draft.getApprovalList());
    List<DocFileResponse> fileList = mapFileList(draft.getFileList());
    List<RefMemberResponse> refList = mapRefList(draft.getRefList());

    return DraftResponse.builder()
            .id(draft.getId())
            .title(draft.getTitle())
            .createdDate(draft.getCreatedDate())
            .documentStatus(draft.getDocumentStatus())
            .emergency(draft.getEmergency())
            .member(mapMemberResponse(member))
            .startDate(draft.getStartDate())
            .content(draft.getContent())
            .fileList(fileList)
            .approvalList(approvalList)
            .documentKind(draft.getDocumentKind())
            .refList(refList)
            .coDept(draft.getCoDept())
            .build();
  }
}
