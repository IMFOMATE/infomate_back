package com.pro.infomate.calendar.entity;

import com.pro.infomate.calendar.dto.ApprovalStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "")
public class FavoriteCalendar {

    @Id
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "REF_CLNDR")
    private Calendar refCalendar;

//    private User refUser;

    @Column(name = "REQUEST_DATE")
    private LocalDateTime requestDate;

    @Column(name = "APPROVALSTAUTS")
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    @Column(name = "LABEL_COLOR")
    private String labelColor;




}
