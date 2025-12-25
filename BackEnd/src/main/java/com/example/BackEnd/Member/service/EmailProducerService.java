package com.example.BackEnd.Member.service;

import com.example.BackEnd.common.dto.EmailMessageDto;
import com.example.BackEnd.common.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import lombok.*;

import static com.example.BackEnd.common.constants.KafkaConstants.*;
import static com.example.BackEnd.common.enums.error_codes.CommonErrorCode.*;

@Service
@RequiredArgsConstructor
public class EmailProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendEmail(EmailMessageDto message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            kafkaTemplate.send(SIGNUP_OTP_TOPIC, jsonMessage);
        } catch (JsonProcessingException e) {
            throw new BusinessException(JSON_PROCESSING_FAILED, e);
        } catch (Exception e) {
            throw new BusinessException(EMAIL_SEND_FAILED, e);
        }
    }

}
