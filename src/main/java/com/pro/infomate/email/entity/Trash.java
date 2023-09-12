package com.pro.infomate.email.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_TRASH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@SequenceGenerator(
        name = "SEQ_TBL_TRASH_GENERATOR",
        sequenceName = "SEQ_TBL_TRASH_TRASH_CODE",
        allocationSize = 1, initialValue = 1
)
public class Trash {

    @Id
    @Column(name = "TRASh_CODE")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_TBL_TRASH_GENERATOR"
    )
    private Integer trashCode;

    @OneToOne
    @JoinColumn(name = "MAIL_CODE", nullable = false)
    private Email mail;
}
