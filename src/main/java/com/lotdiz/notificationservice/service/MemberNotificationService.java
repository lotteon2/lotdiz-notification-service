package com.lotdiz.notificationservice.service;

import com.lotdiz.notificationservice.entity.MemberNotification;
import com.lotdiz.notificationservice.repository.MemberNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberNotificationService {

  private final MemberNotificationRepository memberNotificationRepository;

  public Page<MemberNotification> getMemberNotifications(Long memberId, Pageable pageable) {
    return memberNotificationRepository.findAllByMemberId(memberId, pageable);
  }
}
