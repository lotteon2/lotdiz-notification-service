package com.lotdiz.notificationservice.repository;

import com.lotdiz.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


}
