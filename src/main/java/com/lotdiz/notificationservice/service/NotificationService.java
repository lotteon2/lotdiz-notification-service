package com.lotdiz.notificationservice.service;

import com.lotdiz.notificationservice.dto.CreateProjectDueDateNotificationRequestDto;
import com.lotdiz.notificationservice.dto.CreateProjectFundingRateFailNotificationRequestDto;
import com.lotdiz.notificationservice.entity.MemberNotification;
import com.lotdiz.notificationservice.entity.Notification;
import com.lotdiz.notificationservice.entity.id.MemberNotificationId;
import com.lotdiz.notificationservice.repository.MemberNotificationJdbcRepository;
import com.lotdiz.notificationservice.repository.MemberNotificationRepository;
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
  private final MemberNotificationRepository memberNotificationRepository;

  private final NotificationRepository notificationRepository;
  private final MemberNotificationJdbcRepository memberNotificationJdbcRepository;

  @Transactional
  public void createProjectDueDateNotifications(
      List<CreateProjectDueDateNotificationRequestDto>
          createProjectDueDateNotificationRequestDtos) {
    createProjectDueDateNotificationRequestDtos.forEach(
        pddn -> {
          String projectName = pddn.getProjectName();

          Notification notification =
              Notification.builder()
                  .notificationTitle(String.format("프로젝트[%s] 마감 알림", projectName))
                  .notificationContent(String.format("프로젝트[%s]가 마감되었습니다.", projectName))
                  .build();

          Notification savedNotification = notificationRepository.save(notification);

          List<MemberNotification> memberNotifications =
              pddn.getMemberIds().stream()
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

          MemberNotification makerMemberNotification =
              MemberNotification.builder()
                  .id(
                      MemberNotificationId.builder()
                          .notification(savedNotification)
                          .memberId(pddn.getMakerMemberId())
                          .build())
                  .build();

          memberNotificationRepository.save(makerMemberNotification);
        });
  }

  @Transactional
  public void createProjectFundingRateFailNotification(
      List<CreateProjectFundingRateFailNotificationRequestDto>
          createProjectFundingRateFailNotificationRequestDtos) {
    createProjectFundingRateFailNotificationRequestDtos.forEach(
        pfrfn -> {
          if (!pfrfn.getIsTargetAmountExceed()) {
            String projectName = pfrfn.getProjectName();

            Notification notification =
                Notification.builder()
                    .notificationTitle(String.format("프로젝트[%s] 펀딩 미달성 알림", projectName))
                    .notificationContent(
                        String.format("프로젝트[%s]의 펀딩 달성률이 미달성으로 실패하였습니다.", projectName))
                    .build();

            Notification savedNotification = notificationRepository.save(notification);

            List<MemberNotification> memberNotifications =
                pfrfn.getMemberIds().stream()
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

            MemberNotification makerMemberNotification =
                MemberNotification.builder()
                    .id(
                        MemberNotificationId.builder()
                            .notification(savedNotification)
                            .memberId(pfrfn.getMakerMemberId())
                            .build())
                    .build();

            memberNotificationRepository.save(makerMemberNotification);
          }
        });
  }
}
