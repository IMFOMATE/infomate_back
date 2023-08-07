package com.pro.infomate.approval.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_DRAFT")
@DiscriminatorValue("draft")
public class Draft extends Document{

    @Column(name = "CO_DEPT")
    private String coDept;

}
