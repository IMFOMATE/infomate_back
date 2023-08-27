package com.pro.infomate.approval.entity;

import com.pro.infomate.approval.dto.response.DocumentDetailResponse;
import com.pro.infomate.approval.service.visitor.DocumentVisitor;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "TBL_DRAFT")
@DynamicInsert
@DiscriminatorValue("Draft")
public class Draft extends Document{

    @Column(name = "CO_DEPT")
    private String coDept;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Override
    public DocumentDetailResponse accept(DocumentVisitor<DocumentDetailResponse> visitor) {
        return visitor.visit(this);
    }

}
