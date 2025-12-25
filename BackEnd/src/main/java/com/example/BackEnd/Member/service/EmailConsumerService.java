package com.example.BackEnd.Member.service;

import com.example.BackEnd.common.dto.EmailMessageDto;
import com.example.BackEnd.common.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;
import lombok.*;

import static com.example.BackEnd.common.constants.KafkaConstants.*;
import static com.example.BackEnd.common.enums.error_codes.CommonErrorCode.*;

@Service
@RequiredArgsConstructor
public class EmailConsumerService {

    private final SesClient sesClient;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = SIGNUP_OTP_TOPIC,
            groupId = GROUP_NAME)
    public void consume(String jsonMessage) {
        try {
            EmailMessageDto message = objectMapper.readValue(jsonMessage, EmailMessageDto.class);
            SendEmailRequest emailRequest = createEmailRequest(message);
            sesClient.sendEmail(emailRequest);
        } catch (JsonProcessingException e) {
            throw new BusinessException(JSON_PROCESSING_FAILED, e);
        } catch (Exception e) {
            throw new BusinessException(EMAIL_SEND_FAILED, e);
        }
    }

    private SendEmailRequest createEmailRequest(EmailMessageDto message) {
        return SendEmailRequest.builder()
                .destination(Destination.builder().toAddresses(message.getTo()).build())
                .message(Message.builder()
                        .subject(Content.builder().data(message.getSubject()).build())
                        .body(Body.builder()
                                .text(Content.builder().data(message.getBody()).build())
                                .build())
                        .build())
                .source(message.getFrom())
                .build();
    }

}
