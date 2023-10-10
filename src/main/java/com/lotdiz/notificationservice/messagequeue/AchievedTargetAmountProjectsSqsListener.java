package com.lotdiz.notificationservice.messagequeue;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotdiz.notificationservice.dto.request.TargetAmountAchievedProjectsDto;
import com.lotdiz.notificationservice.service.AchievedTargetAmountProjectsNotificationService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievedTargetAmountProjectsSqsListener {
  private final ObjectMapper objectMapper;
  private final AchievedTargetAmountProjectsNotificationService
      achievedTargetAmountProjectsNotificationService;

  @SqsListener(
      value = "${cloud.aws.sqs.achieved-target-amount-notification-queue.name}",
      deletionPolicy = SqsMessageDeletionPolicy.NEVER)
  public void createAchievedTargetAmountProjectsNotification(
      @Payload String message, @Headers Map<String, String> headers, Acknowledgment ack)
      throws JsonProcessingException {
    TargetAmountAchievedProjectsDto targetAmountAchievedProjectsDtos =
        objectMapper.readValue(message, TargetAmountAchievedProjectsDto.class);
    // TODO: 여기서 부터 다시 - 알림 저장하고 사용자 알림 등록 해야 함
    ack.acknowledge();

    achievedTargetAmountProjectsNotificationService.createAchievedTargetAmountProjectNotification(
        targetAmountAchievedProjectsDtos);
  }
}
