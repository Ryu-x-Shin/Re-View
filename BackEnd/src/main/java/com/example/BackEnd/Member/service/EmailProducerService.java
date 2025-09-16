package com.example.BackEnd.Member.service;

import com.example.BackEnd.common.dto.EmailMessageDto;
import com.example.BackEnd.common.enums.error_codes.GlobalError;
import com.example.BackEnd.common.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendEmail(EmailMessageDto message) {
        try {
            String json = objectMapper.writeValueAsString(message);
            kafkaTemplate.send("member.signup.OTPEmail", json);
        } catch (JsonProcessingException e) {
            throw new BusinessException(GlobalError.JSON_PROCESSING_ERROR, e);
        } catch (Exception e) {
            throw new BusinessException(GlobalError.EMAIL_SEND_FAILED, e);
        }
    }
}
