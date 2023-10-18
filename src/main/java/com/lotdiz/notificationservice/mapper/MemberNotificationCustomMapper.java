package com.lotdiz.notificationservice.mapper;

import com.lotdiz.notificationservice.dto.response.GetNotificationResponseDto;
import com.lotdiz.notificationservice.entity.MemberNotification;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MemberNotificationCustomMapper {

  public List<GetNotificationResponseDto> memberNotificationsToGetNotificationResponseDtos(
      List<MemberNotification> memberNotifications) {
    return memberNotifications.stream()
        .map(
            mn ->
                GetNotificationResponseDto.builder()
                    .notificationId(mn.getId().getNotification().getId())
                    .notificationTitle(mn.getId().getNotification().getNotificationTitle())
                    .notificationContent(mn.getId().getNotification().getNotificationContent())
                    .notificationIsRead(mn.getMemberNotificationIsRead())
                    .createdAt(mn.getId().getNotification().getCreatedAt())
                    .updatedAt(mn.getId().getNotification().getUpdatedAt())
                    .build())
        .collect(Collectors.toList());
  }
}
