package com.pro.infomate.BoardWork.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_BOARDFILE")
public class BoardFile {

    @Id
    @Column(name = "FILE_NO")
    private int fileNo;

    @Column(name = "POST_CODE")
    private int postCode;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_ORIGINAL")
    private String fileOriginal;



    @ManyToOne
    @JoinColumn(name = "BOARD_CODE")
    private Board boardCode;


}
