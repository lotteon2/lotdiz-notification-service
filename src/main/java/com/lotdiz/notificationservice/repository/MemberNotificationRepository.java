package com.lotdiz.notificationservice.repository;

import com.lotdiz.notificationservice.entity.MemberNotification;
import com.lotdiz.notificationservice.entity.id.MemberNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberNotificationRepository extends JpaRepository<MemberNotification, MemberNotificationId> {

}
