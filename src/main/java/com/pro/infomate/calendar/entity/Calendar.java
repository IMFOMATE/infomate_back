package com.pro.infomate.calendar.entity;


import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@DynamicUpdate
@DynamicInsert
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
    private Integer indexNo;

    @Column(name = "REF_MEMBER_CODE")
    private Integer memberCode;

    @Column(name = "DEFAULT_SELC")
    private Boolean defaultCalendar;

    @Column(name = "DPRMT_CODE")
    private Integer departmentCode;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "calendar")
    private List<FavoriteCalendar> favoriteCalendar;

    @OneToMany(mappedBy = "calendar")
    private List<Schedule> schedule;

    @ManyToOne
    @JoinColumn(name = "REF_MEMBER_CODE",insertable = false ,updatable = false)
    private Member member;

    public void update(CalendarDTO calendarDTO){
        if(calendarDTO.getName() != null) this.name = calendarDTO.getName();
        if(calendarDTO.getOpenStatus() != null) this.openStatus = calendarDTO.getOpenStatus();
        if(calendarDTO.getLabelColor() != null) this.labelColor = calendarDTO.getLabelColor();
        if(calendarDTO.getIndexNo() != null) this.indexNo = calendarDTO.getIndexNo();
        if(calendarDTO.getMemberCode() != null) this.memberCode = calendarDTO.getMemberCode();
        if(calendarDTO.getDepartmentCode() != null) this.departmentCode = calendarDTO.getDepartmentCode();
        if(calendarDTO.getDefaultCalendar() != null) this.defaultCalendar = calendarDTO.getDefaultCalendar();
    }
    @Builder
    public Calendar(int id, String name, Boolean openStatus, String labelColor, Integer indexNo, Integer memberCode, Boolean defaultCalendar, Integer departmentCode, LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.openStatus = openStatus;
        this.labelColor = labelColor;
        this.indexNo = indexNo;
        this.memberCode = memberCode;
        this.defaultCalendar = defaultCalendar;
        this.departmentCode = departmentCode;
        this.createDate = createDate;
    }

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
