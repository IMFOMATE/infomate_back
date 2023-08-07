package com.pro.infomate.email.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_PHOTO_FILE")
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
public class PhotoFile {

    @Id
    @Column(name = "FILE_CODE")
    private int fileCode;

    @ManyToOne
    @JoinColumn(name = "MAIL_CODE", nullable = false)
    private Email mail;

    @Column(name = "FILE_DATE")
    private String fileDate;

    @Column(name = "FILE_SOURCE_NAME")
    private String fileSourceName;

    @Column(name = "FILE_MODIFICATION_NAME")
    private String fileModificationName;
}
