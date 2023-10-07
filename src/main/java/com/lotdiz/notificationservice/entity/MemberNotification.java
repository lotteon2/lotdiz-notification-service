package com.lotdiz.notificationservice.entity;

import com.lotdiz.notificationservice.entity.common.BaseEntity;
import com.lotdiz.notificationservice.entity.id.MemberNotificationId;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * EmbeddedId 로 복합키임을 정의
 *
 * <p>select 시 id.memberId, id.notification.id 로 조회(MemberNotificationTest 에 insert, select 테스트 구성)
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberNotification extends BaseEntity {
  @EmbeddedId private MemberNotificationId id;

  @Builder.Default
  @Column(
      name = "member_notification_is_read",
      nullable = false,
      columnDefinition = "boolean default false")
  private Boolean memberNotificationIsRead = false;
}
