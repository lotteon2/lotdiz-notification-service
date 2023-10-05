package com.lotdiz.notification.repository;

import com.lotdiz.notificationservice.NotificationServiceApplication;
import com.lotdiz.notificationservice.entity.MemberNotification;
import com.lotdiz.notificationservice.entity.Notification;
import com.lotdiz.notificationservice.entity.id.MemberNotificationId;
import com.lotdiz.notificationservice.repository.MemberNotificationRepository;
import com.lotdiz.notificationservice.repository.NotificationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = NotificationServiceApplication.class)
public class MemberNotificationRepositoryTest {
  @Autowired MemberNotificationRepository memberNotificationRepository;
  @Autowired NotificationRepository notificationRepository;

  @Test
  @DisplayName("member notification insert test")
  void insertMemberNotificationTest() {
    Long memberId = 1L;
    Notification notification =
        Notification.builder()
            .notificationContent("notificationContent")
            .notificationTitle("notificationTitle")
            .build();
    Notification savedNotification = notificationRepository.save(notification);
    MemberNotification memberNotification =
        MemberNotification.builder()
            .memberNotificationIsRead(false)
            .id(
                MemberNotificationId.builder()
                    .notification(savedNotification)
                    .memberId(memberId)
                    .build())
            .build();
    MemberNotification savedMemberNotification =
        memberNotificationRepository.save(memberNotification);

    Assertions.assertThat(savedMemberNotification.getId().getNotification().getNotificationTitle())
        .isEqualTo(notification.getNotificationTitle());
  }
}
