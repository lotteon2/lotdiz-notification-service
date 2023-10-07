package com.lotdiz.notificationservice.service;

import com.lotdiz.notificationservice.dto.CreateProjectDueDateNotificationRequestDto;
import com.lotdiz.notificationservice.dto.CreateProjectFundingRateFailNotificationRequestDto;
import com.lotdiz.notificationservice.entity.MemberNotification;
import com.lotdiz.notificationservice.entity.Notification;
import com.lotdiz.notificationservice.entity.id.MemberNotificationId;
import com.lotdiz.notificationservice.repository.MemberNotificationJdbcRepository;
import com.lotdiz.notificationservice.repository.NotificationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationRepository notificationRepository;
  private final MemberNotificationJdbcRepository memberNotificationJdbcRepository;

  @Transactional
  public void createProjectDueDateNotification(
      CreateProjectDueDateNotificationRequestDto createProjectDueDateNotificationRequestDto) {
    String projectName = createProjectDueDateNotificationRequestDto.getProjectName();

    Notification notification =
        Notification.builder()
            .notificationTitle(String.format("프로젝트[%s] 마감 알림", projectName))
            .notificationContent(String.format("프로젝트[%s]가 마감되었습니다.", projectName))
            .build();

    Notification savedNotification = notificationRepository.save(notification);

    List<MemberNotification> memberNotifications =
        createProjectDueDateNotificationRequestDto.getMemberIds().stream()
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

  @Transactional
  public void createProjectFundingRateFailNotification(
      CreateProjectFundingRateFailNotificationRequestDto
          createProjectFundingRateFailNotificationRequestDto) {
    String projectName = createProjectFundingRateFailNotificationRequestDto.getProjectName();

    Notification notification =
        Notification.builder()
            .notificationTitle(String.format("프로젝트[%s] 펀딩 미달성 알림", projectName))
            .notificationContent(String.format("프로젝트[%s]의 펀딩 달성률이 미달성으로 실패하였습니다.", projectName))
            .build();

    Notification savedNotification = notificationRepository.save(notification);

    List<MemberNotification> memberNotifications =
        createProjectFundingRateFailNotificationRequestDto.getMemberIds().stream()
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
