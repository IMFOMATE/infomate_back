package com.pro.infomate.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_AUTHLIST")
@IdClass(AuthListPK.class)
@Getter
@Setter
@ToString
public class AuthList {

    @Id
    @Column(name = "MEMBER_CODE")
    private int memberCode;

    @Id
    @Column(name = "AUTHORITY_CODE")
    private int authorityCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "AUTHORITY_CODE", insertable = false, updatable = false)
    private Authority authority;

    public AuthList() {
    }

    public AuthList(int memberCode, int authorityCode) {
        this.memberCode = memberCode;
        this.authorityCode = authorityCode;
    }

    public AuthList(int memberCode, int authorityCode, Authority authority) {
        this.memberCode = memberCode;
        this.authorityCode = authorityCode;
        this.authority = authority;
    }
}
