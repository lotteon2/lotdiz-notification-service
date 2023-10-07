package com.lotdiz.notificationservice.service;

import com.lotdiz.notificationservice.dto.response.GetNotificationDetailResponseDto;
import com.lotdiz.notificationservice.entity.MemberNotification;
import com.lotdiz.notificationservice.repository.MemberNotificationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberNotificationService {

  private final MemberNotificationRepository memberNotificationRepository;

  public List<GetNotificationDetailResponseDto> getNotificationDetails(
      Long memberId, Pageable pageable) {
    List<MemberNotification> memberNotifications =
        memberNotificationRepository.findAllByMemberId(memberId, pageable);
    return memberNotifications.stream()
        .map(
            mn ->
                GetNotificationDetailResponseDto.builder()
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
