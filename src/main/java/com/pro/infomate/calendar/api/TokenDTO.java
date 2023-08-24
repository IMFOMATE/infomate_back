package com.pro.infomate.calendar.api;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TokenDTO {

    private String jwt;

    private String pk;

    private String username;

    private LocalDateTime expire;
}
