package com.pro.infomate.calendar.entity;


import com.pro.infomate.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "TBL_CLNDR")
@SequenceGenerator(
        name = "SEQ_TBL_CLNDR_GEN",
        sequenceName = "SEQ_TBL_CLNDR_CLNDR_CODE",
        initialValue = 1, allocationSize = 1
)
public class Calendar {

    @Id
    @Column(name = "CLNDR_CODE")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_TBL_CLNDR_GEN"
    )
    private int id;

    @Column(name = "CLNDR_NAME")
    private String name;

    @Column(name = "OPEN_STATUS")
    private Boolean openStatus;

    @Column(name = "LABEL_COLOR")
    private String labelColor;

    @Column(name = "INDEX_NUM")
    private int indexNo;

    @Column(name = "REF_MEMBER_CODE")
    private int memberCode;

    @Column(name = "DEFAULT_SELC")
    private Boolean defaultCalendar;

    @Column(name = "DPRMT_CODE")
    private Integer departmentCode;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "calendar", fetch = FetchType.LAZY, targetEntity = FavoriteCalendar.class)
    private List<FavoriteCalendar> favoriteCalendar;

    @OneToMany(mappedBy = "calendar", fetch = FetchType.LAZY,targetEntity = Schedule.class)
    private List<Schedule> schedule;

    @ManyToOne
    @JoinColumn(name = "REF_MEMBER_CODE",insertable = false ,updatable = false)
    private Member member;

    @Override
    public String toString() {
        return "Calendar{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", openStatus=" + openStatus +
                ", labelColor='" + labelColor + '\'' +
                ", indexNo=" + indexNo +
                ", memberCode=" + memberCode +
                ", defaultCalendar=" + defaultCalendar +
                ", departmentCode=" + departmentCode +
                ", createDate=" + createDate +
                '}';
    }
}
