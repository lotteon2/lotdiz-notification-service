package com.lotdiz.notificationservice.repository;

import com.lotdiz.notificationservice.entity.MemberNotification;
import com.lotdiz.notificationservice.entity.id.MemberNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberNotificationRepository
    extends JpaRepository<MemberNotification, MemberNotificationId> {
  @Query("select count(noti) from MemberNotification noti where noti.id.memberId = :memberId and noti.memberNotificationIsRead=false ")
  Long unreadNotificationCount(Long memberId);
}
