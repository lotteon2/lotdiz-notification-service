package com.lotdiz.notificationservice.service;

import com.lotdiz.notificationservice.dto.request.CreateProjectDueDateNotificationRequestDto;
import com.lotdiz.notificationservice.dto.request.CreateProjectFundingRateFailNotificationRequestDto;
import com.lotdiz.notificationservice.dto.request.DeliveryStartNotificationRequestDto;
import com.lotdiz.notificationservice.entity.MemberNotification;
import com.lotdiz.notificationservice.entity.Notification;
import com.lotdiz.notificationservice.entity.id.MemberNotificationId;
import com.lotdiz.notificationservice.repository.MemberNotificationRepository;
import com.lotdiz.notificationservice.repository.NotificationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

  private static final String PROJECT_DUE_DATE_TITLE_FORMAT = "프로젝트[%s] 마감 알림";
  private static final String PROJECT_DUE_DATE_CONTENT_FORMAT = "프로젝트[%s] 마감 알림";
  private static final String PROJECT_FUNDING_RATE_FAIL_TITLE_FORMAT = "프로젝트[%s] 펀딩 미달성 알림";
  private static final String PROJECT_FUNDING_RATE_FAIL_CONTENT_FORMAT =
      "프로젝트[%s]의 펀딩 달성률이 미달성으로 실패하였습니다.";

  private final MemberNotificationRepository memberNotificationRepository;
  private final NotificationRepository notificationRepository;

  @Transactional
  public void createProjectDueDateNotifications(
      List<CreateProjectDueDateNotificationRequestDto>
          createProjectDueDateNotificationRequestDtos) {

    List<Notification> totalNotifications = new ArrayList<>();
    createProjectDueDateNotificationRequestDtos.forEach(
        pddn -> {
          String projectName = pddn.getProjectName();
          Notification notification =
              createNotification(
                  String.format(PROJECT_DUE_DATE_TITLE_FORMAT, projectName),
                  String.format(PROJECT_DUE_DATE_CONTENT_FORMAT, projectName),
                  pddn.getMemberIds(),
                  pddn.getMakerMemberId());

          totalNotifications.add(notification);
        });

    notificationRepository.saveAll(totalNotifications);
  }

  @Transactional
  public void createProjectFundingRateFailNotification(
      List<CreateProjectFundingRateFailNotificationRequestDto>
          createProjectFundingRateFailNotificationRequestDtos) {

    List<Notification> totalNotifications = new ArrayList<>();
    createProjectFundingRateFailNotificationRequestDtos.forEach(
        pfrfn -> {
          if (!pfrfn.getIsTargetAmountExceed()) {
            String projectName = pfrfn.getProjectName();
            Notification notification =
                createNotification(
                    String.format(PROJECT_FUNDING_RATE_FAIL_TITLE_FORMAT, projectName),
                    String.format(PROJECT_FUNDING_RATE_FAIL_CONTENT_FORMAT, projectName),
                    pfrfn.getMemberIds(),
                    pfrfn.getMakerMemberId());

            totalNotifications.add(notification);
          }
        });

    if (!totalNotifications.isEmpty()) {
      notificationRepository.saveAll(totalNotifications);
    }
  }

  @Transactional
  public void createDeliveryStartNotification(
      DeliveryStartNotificationRequestDto deliveryStartNotificationRequestDto) {

    // notification 생성
    Notification notification =
        Notification.builder()
            .notificationTitle("배송 시작 알림")
            .notificationContent(
                String.format(
                    "%s 님의 %s 프로젝트 배송이 시작되었습니다!",
                    deliveryStartNotificationRequestDto.getMemberName(),
                    deliveryStartNotificationRequestDto.getProjectName()))
            .build();
    Notification savedNotification = notificationRepository.save(notification);

    // member notification 셍성
    MemberNotification memberNotification =
        MemberNotification.builder()
            .id(
                MemberNotificationId.builder()
                    .memberId(deliveryStartNotificationRequestDto.getMemberId())
                    .notification(savedNotification)
                    .build())
            .build();

    memberNotificationRepository.save(memberNotification);
  }

  @NotNull
  private static Notification createNotification(
      String notificationTitle,
      String notificationContent,
      List<Long> memberIds,
      Long makerMemberId) {
    List<MemberNotification> memberNotifications =
        memberIds.stream()
            .map(
                memberId ->
                    MemberNotification.createMemberNotification(
                        MemberNotificationId.createMemberNotificationId(memberId)))
            .collect(Collectors.toList());

    memberNotifications.add(
        MemberNotification.createMemberNotification(
            MemberNotificationId.createMemberNotificationId(makerMemberId)));

    Notification notification =
        Notification.createNotification(
            notificationTitle, notificationContent, memberNotifications);
    return notification;
  }
}
