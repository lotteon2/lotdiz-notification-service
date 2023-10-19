package com.lotdiz.notificationservice.entity.id;

import com.lotdiz.notificationservice.entity.Notification;
import java.io.Serializable;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Embeddable 을 사용해 notification 복합키로 구성
 *
 * <p>join이 필요한 Notification 클래스를 정의
 */
@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberNotificationId implements Serializable {
  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "notification_id")
  private Notification notification;

  public static MemberNotificationId createMemberNotificationId(Long memberId) {
    return MemberNotificationId.builder().memberId(memberId).build();
  }

  public void setNotification(Notification notification) {
    this.notification = notification;
  }
}
