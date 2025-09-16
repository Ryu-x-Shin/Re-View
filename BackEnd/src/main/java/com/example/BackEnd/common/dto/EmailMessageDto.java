package com.example.BackEnd.common.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailMessageDto {

    private String from;

    private String to;

    private String subject;

    private String body;

}
