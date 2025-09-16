package com.example.BackEnd.Member.service;

import com.example.BackEnd.common.dto.EmailMessageDto;
import com.example.BackEnd.common.enums.error_codes.GlobalError;
import com.example.BackEnd.common.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
@RequiredArgsConstructor
public class EmailConsumerService {

    private final SesClient sesClient;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "member.signup.OTPEmail",
            groupId = "signup-email-group")
    public void consume(String messageJson) {
        try {
            EmailMessageDto message = objectMapper.readValue(messageJson, EmailMessageDto.class);

            SendEmailRequest request = SendEmailRequest.builder()
                    .destination(Destination.builder().toAddresses(message.getTo()).build())
                    .message(Message.builder()
                            .subject(Content.builder().data(message.getSubject()).build())
                            .body(Body.builder()
                                    .text(Content.builder().data(message.getBody()).build())
                                    .build())
                            .build())
                    .source(message.getFrom())
                    .build();


            long start = System.currentTimeMillis();
            sesClient.sendEmail(request);
            long end = System.currentTimeMillis();
            System.out.println(end - start);

        } catch (JsonProcessingException e) {
            throw new BusinessException(GlobalError.JSON_PROCESSING_ERROR, e);
        } catch (Exception e) {
            throw new BusinessException(GlobalError.EMAIL_SEND_FAILED, e);
        }
    }
}
