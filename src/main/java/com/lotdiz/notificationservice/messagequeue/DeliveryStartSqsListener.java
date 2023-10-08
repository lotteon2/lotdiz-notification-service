package com.lotdiz.notificationservice.messagequeue;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class DeliveryStartSqsListener {
  private final ObjectMapper objectMapper;
  private final NotificationService notificationService;

  @SqsListener(
      value = "${cloud.aws.sqs.delivery-start-notification-queue.name}",
      deletionPolicy = SqsMessageDeletionPolicy.NEVER)
  public void createDeliveryStartNotification(
      @Payload String message, @Headers Map<String, String> headers, Acknowledgment ack) {
    log.info("receive from DeliveryStartSqsListener message={}", message);


    ack.acknowledge();
  }
}
