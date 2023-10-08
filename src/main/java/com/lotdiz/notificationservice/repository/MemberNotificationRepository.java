package com.lotdiz.notificationservice.repository;

import com.lotdiz.notificationservice.entity.MemberNotification;
import com.lotdiz.notificationservice.entity.id.MemberNotificationId;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberNotificationRepository
    extends JpaRepository<MemberNotification, MemberNotificationId> {

  @Query(
      "select mn from MemberNotification mn "
          + "join fetch mn.id.notification n "
          + "where mn.id.memberId = :memberId")
  List<MemberNotification> findAllByMemberId(@Param("memberId") Long memberId, Pageable pageable);

  @Query(
      "select count(noti) from MemberNotification noti where noti.id.memberId = :memberId and noti.memberNotificationIsRead=false ")
  Long getUnreadNotificationCount(Long memberId);
}
