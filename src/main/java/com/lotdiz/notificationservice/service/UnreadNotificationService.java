package com.lotdiz.notificationservice.service;

import com.lotdiz.notificationservice.repository.MemberNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnreadNotificationService {
  private final MemberNotificationRepository memberNotificationRepository;

  /**
   * 안읽은 알림 개수
   *
   * @param memberId 사용자 id
   * @return Long
   */
  public Long getNumberOfUnreadNotifications(Long memberId) {
    return memberNotificationRepository.getUnreadNotificationCount(memberId);
  }
}
