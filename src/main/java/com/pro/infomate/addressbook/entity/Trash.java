package com.pro.infomate.addressbook.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_TRASH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(
        name = "SEQ_TBL_FILE_GENERATOR",
        sequenceName = "SEQ_TBL_FILE_CODE",
        allocationSize = 1, initialValue = 1
)
public class Trash {

    @Id
    @Column(name = "TRASh_CODE")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_TBL_FILE_GENERATOR"
    )
    private int trashCode;

    @ManyToOne
    @JoinColumn(name = "MAIL_CODE", nullable = false)
    private Email mail;
}
