package com.lotdiz.notificationservice.service;

import com.lotdiz.notificationservice.entity.MemberNotification;
import com.lotdiz.notificationservice.repository.MemberNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberNotificationService {

  private final MemberNotificationRepository memberNotificationRepository;

  public Page<MemberNotification> getMemberNotifications(Long memberId, Pageable pageable) {
    return memberNotificationRepository.findAllByMemberId(memberId, pageable);
  }

  public int setMemberNotificationIsReadByIdIsIn(Long memberId, List<Long> notificationIds) {
    int updatedRow =
        memberNotificationRepository.setMemberNotificationIsReadByIdIsIn(memberId, notificationIds);
    if (updatedRow != notificationIds.size()) {
      throw new RuntimeException("알림 업데이트가 정상적으로 되지 않았습니다");
    }

    return updatedRow;
  }
}
