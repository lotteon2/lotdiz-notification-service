package com.lotdiz.notification.entity;

import com.lotdiz.notificationservice.NotificationServiceApplication;
import com.lotdiz.notificationservice.entity.MemberNotification;
import com.lotdiz.notificationservice.entity.Notification;
import com.lotdiz.notificationservice.entity.id.MemberNotificationId;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = NotificationServiceApplication.class)
@Transactional
public class MemberNotificationTest {
  @Autowired EntityManager em;

  @Test
  @DisplayName("member notification 복합키 테스트")
  void memberNotificationCompositeKeyTest() {
    Long memberId = 1L;

    // when
    Notification notification =
        Notification.builder()
            .notificationContent("notificationContent")
            .notificationTitle("notificationTitle")
            .build();
    // notification 저장
    em.persist(notification);
    em.flush();
    em.clear();

    // then
    Notification notification1 = em.find(Notification.class, 1L);
    MemberNotification memberNotification =
        MemberNotification.builder()
            .memberNotificationIsRead(true)
            .id(
                MemberNotificationId.builder()
                    .memberId(memberId)
                    .notification(notification1)
                    .build())
            .build();
    em.persist(memberNotification);
    em.flush();
    em.clear();

    // then
    MemberNotification memberNotification1 =
        (MemberNotification)
            em.createQuery(
                    "select m from MemberNotification m where m.id.memberId=1 and m.id.notification.id=1")
                .getSingleResult();
    MemberNotificationId id = memberNotification1.getId();
    Assertions.assertThat(id.getNotification().getNotificationTitle())
        .isEqualTo(notification.getNotificationTitle());
  }
}
