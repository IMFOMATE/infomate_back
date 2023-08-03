package com.pro.infomate.calendar.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "")
public class Participant {

    @Id
    @Column(name = "ID")
    private int id;

    //    private User user;

    @ManyToOne
    @JoinColumn(name = "REF_SCHDL")
    private Schedule schedule;
}
