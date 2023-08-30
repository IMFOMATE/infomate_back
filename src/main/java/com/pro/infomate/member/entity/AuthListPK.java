package com.pro.infomate.member.entity;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthListPK implements Serializable {


    private int memberCode;

    private int authorityCode;
}
