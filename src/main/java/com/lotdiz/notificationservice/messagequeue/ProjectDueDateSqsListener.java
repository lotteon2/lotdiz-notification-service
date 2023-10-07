package com.lotdiz.notificationservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotdiz.notificationservice.dto.CreateProjectDueDateNotificationRequestDto;
import com.lotdiz.notificationservice.dto.CreateProjectFundingRateFailNotificationRequestDto;
import com.lotdiz.notificationservice.service.NotificationService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectDueDateSqsListener {

  private final ObjectMapper objectMapper;
  private final NotificationService notificationService;

  @SqsListener(
      value = "${cloud.aws.sqs.project-due-date-notification-queue.name}",
      deletionPolicy = SqsMessageDeletionPolicy.NEVER)
  public void createProjectDueDateNotificationQueue(
      @Payload String message, @Headers Map<String, String> headers, Acknowledgment ack) {
    log.info("receive ProjectDueDateNotification message={}", message);
    try {
      CreateProjectDueDateNotificationRequestDto createProjectDueDateNotificationRequestDto =
          objectMapper.readValue(message, CreateProjectDueDateNotificationRequestDto.class);

      notificationService.createProjectDueDateNotification(
          createProjectDueDateNotificationRequestDto);

      ack.acknowledge();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @SqsListener(
      value = "${cloud.aws.sqs.project-funding-rate-fail-notification-queue.name}",
      deletionPolicy = SqsMessageDeletionPolicy.NEVER)
  public void createProjectFundingRateFailNotificationQueue(
      @Payload String message, @Headers Map<String, String> headers, Acknowledgment ack) {
    log.info("receive ProjectFundingRateFailNotification message={}", message);
    try {
      CreateProjectFundingRateFailNotificationRequestDto
          createProjectFundingRateFailNotificationRequestDto =
              objectMapper.readValue(
                  message, CreateProjectFundingRateFailNotificationRequestDto.class);

      if (!createProjectFundingRateFailNotificationRequestDto.getIsSufficientFundingAmount()) {
        notificationService.createProjectFundingRateFailNotification(
            createProjectFundingRateFailNotificationRequestDto);
      }

      ack.acknowledge();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
