package com.pro.infomate.calendar.entity;

import com.pro.infomate.calendar.dto.ApprovalStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Builder
@Table(name = "TBL_FVRT_CLNDR")
@SequenceGenerator(
        name = "SEQ_TBL_FVRT_CLNDR_GEN",
        sequenceName = "SEQ_TBL_FVRT_CLNDR_FVRT_CLNDR_ID",
        initialValue = 1, allocationSize = 1
)
public class FavoriteCalendar {

    @Id
    @Column(name = "FVRT_CLNDR_ID")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_TBL_FVRT_CLNDR_GEN"
    )
    private int id;

    @Column(name = "REF_CLDNR_ID")
    private int refCalendar;

    @Column(name = "REF_MEMBER_CODE")
    private int memberCode; //수정요망


    @Column(name = "REQUEST_DATE")
    private LocalDateTime requestDate;

    @Column(name = "APPROVAL_STATUS")
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    @Column(name = "LABEL_COLOR")
    private String labelColor;


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Calendar.class)
    @JoinColumn(name = "REF_CLDNR_ID", insertable = false, updatable = false)
    private Calendar calendar;

    //    @ManyToOne
//    @JoinColumn(name = "REF_MEMBER_CODE")
//    private int member;
}
