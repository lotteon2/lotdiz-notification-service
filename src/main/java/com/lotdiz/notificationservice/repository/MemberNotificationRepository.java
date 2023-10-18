package com.lotdiz.notificationservice.repository;

import com.lotdiz.notificationservice.entity.MemberNotification;
import com.lotdiz.notificationservice.entity.id.MemberNotificationId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberNotificationRepository
    extends JpaRepository<MemberNotification, MemberNotificationId> {

  // fetch join 사용과 동시에 pagable을 주입하면 paging에 필요한 count 쿼리를 만들지 못하므로 countQuery를 명시
  @Query(
      value =
          "select mn from MemberNotification mn "
              + "join fetch mn.id.notification n "
              + "where mn.id.memberId = :memberId",
      countQuery = "select count(mn) from MemberNotification mn where mn.id.memberId = :memberId")
  Page<MemberNotification> findAllByMemberId(@Param("memberId") Long memberId, Pageable pageable);

  @Query(
      "select count(noti) from MemberNotification noti where noti.id.memberId = :memberId and noti.memberNotificationIsRead=false ")
  Long getUnreadNotificationCount(Long memberId);
}
