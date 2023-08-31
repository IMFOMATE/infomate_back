package com.pro.infomate.member.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RankDTO {

    private Long rankCode;

    private String rankName;

    private int rankPlace;

}
