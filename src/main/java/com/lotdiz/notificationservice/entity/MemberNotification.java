package com.lotdiz.notificationservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberNotification extends BaseEntity {
  @Id
  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @Column(name = "member_notification_is_read", nullable = false)
  private Boolean memberNotificationIsRead;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "notification_id")
  private Notification notification;
}
