package com.pro.infomate.approval.dto.response;

import lombok.*;

import java.util.List;


@Setter
@Getter
@ToString
@NoArgsConstructor
public class MainCreditResponse {
    
    
    private int creditCount;
    
    private int approvalCount;
    
    private int doneList;
    
    private List<DocumentListResponse> creditList;

    @Builder
    public MainCreditResponse(int creditCount, int approvalCount, int doneList, List<DocumentListResponse> creditList) {
        this.creditCount = creditCount;
        this.approvalCount = approvalCount;
        this.doneList = doneList;
        this.creditList = creditList;
    }
}
