package com.lotdiz.notificationservice.service;

import com.lotdiz.notificationservice.dto.request.TargetAmountAchievedProjectsDto;
import com.lotdiz.notificationservice.entity.MemberNotification;
import com.lotdiz.notificationservice.entity.Notification;
import com.lotdiz.notificationservice.entity.id.MemberNotificationId;
import com.lotdiz.notificationservice.repository.MemberNotificationJdbcRepository;
import com.lotdiz.notificationservice.repository.NotificationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievedTargetAmountProjectsNotificationService {
  private final NotificationRepository notificationRepository;
  private final MemberNotificationJdbcRepository memberNotificationJdbcRepository;

  public void createAchievedTargetAmountProjectNotification(
      TargetAmountAchievedProjectsDto targetAmountAchievedProjectsDtos) {
    // create notification
    Notification notification =
        Notification.builder()
            .notificationTitle("프로젝트 목표 금액 달성")
            .notificationContent(
                String.format(
                    "%s프로젝트가 목표 금액을 달성했습니다!", targetAmountAchievedProjectsDtos.getProjectName()))
            .build();
    Notification savedNotification = notificationRepository.save(notification);

    // 각 member 별로 member_notification 생성
    List<MemberNotification> memberNotifications =
        targetAmountAchievedProjectsDtos.getMemberIds().stream()
            .map(
                memberId ->
                    MemberNotification.builder()
                        .id(
                            MemberNotificationId.builder()
                                .notification(savedNotification)
                                .memberId(memberId)
                                .build())
                        .build())
            .collect(Collectors.toList());
    memberNotificationJdbcRepository.batchInsert(memberNotifications);
  }
}
