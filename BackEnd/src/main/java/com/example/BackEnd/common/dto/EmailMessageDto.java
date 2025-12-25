package com.example.BackEnd.common.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class EmailMessageDto {

    private String from;
    private String to;
    private String subject;
    private String body;

}
