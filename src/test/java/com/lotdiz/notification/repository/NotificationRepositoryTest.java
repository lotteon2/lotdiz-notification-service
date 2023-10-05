package com.lotdiz.notification.repository;

import com.lotdiz.notificationservice.NotificationServiceApplication;
import com.lotdiz.notificationservice.entity.Notification;
import com.lotdiz.notificationservice.repository.NotificationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = NotificationServiceApplication.class)
public class NotificationRepositoryTest {
  @Autowired private NotificationRepository notificationRepository;

  @Test
  @DisplayName("notification insert test")
  void notificationInsertTest() {
    Notification notification =
        Notification.builder()
            .notificationTitle("noti title")
            .notificationContent("noti content")
            .build();
    Notification savedNotification = notificationRepository.save(notification);

    Assertions.assertThat(notification.getNotificationTitle())
        .isEqualTo(savedNotification.getNotificationTitle());
  }
}
