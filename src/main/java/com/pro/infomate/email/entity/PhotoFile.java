package com.pro.infomate.email.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TBL_PHOTO_FILE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(
        name = "SEQ_TBL_FILE_GENERATOR",
        sequenceName = "SEQ_TBL_PHOTO_FILE_FILE_CODE",
        allocationSize = 1, initialValue = 1
)
@DynamicInsert
public class PhotoFile {

    @Id
    @Column(name = "FILE_CODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBL_FILE_GENERATOR")
    private Integer fileCode;

    @ManyToOne
    @JoinColumn(name = "MAIL_CODE", nullable = false)
    private Email mail;

    @Column(name = "FILE_DATE")
    private Date fileDate;


    @Column(name = "FILE_MODIFICATION_NAME")
    private String fileModificationName;
}
