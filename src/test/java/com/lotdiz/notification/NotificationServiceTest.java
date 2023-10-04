package com.lotdiz.notification;

import com.lotdiz.notificationservice.NotificationServiceApplication;
import com.lotdiz.notificationservice.entity.Notification;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = NotificationServiceApplication.class)
public class NotificationServiceTest {
  Logger log = Logger.getLogger("notification");
  @Autowired private EntityManager em;

  @Test
  void notificationInsertTest() {
    // given
    Notification notification =
        Notification.builder()
            .notificationContent("notificationContent")
            .notificationTitle("notificationTitle")
            .build();

    // when
    em.persist(notification);
    em.flush();
    em.clear();

    // then
    Notification notification1 = em.find(Notification.class, 1L);
    log.info(notification1.getNotificationContent());
    Assertions.assertThat(notification1.getId()).isEqualTo(1L);
  }
}
