package com.lotdiz.notificationservice.repository;

import com.lotdiz.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
  //  int selectUnreadNotificationCount(Long memberId);
}
