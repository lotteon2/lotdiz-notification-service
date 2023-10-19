package com.lotdiz.notificationservice.entity;

import com.lotdiz.notificationservice.entity.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
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
@SequenceGenerator(
    name = "NOTIFICATION_SEQ_GENERATOR",
    sequenceName = "NOTIFICATION_SEQ",
    initialValue = 1,
    allocationSize = 50)
public class Notification extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTIFICATION_SEQ_GENERATOR")
  @Column(name = "notification_id")
  private Long id;

  @OneToMany(
      mappedBy = "id.notification",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      orphanRemoval = true)
  @Builder.Default
  private List<MemberNotification> memberNotifications = new ArrayList<>();

  @Column(name = "notification_title", nullable = false)
  private String notificationTitle;

  @Column(name = "notification_content", nullable = false)
  private String notificationContent;

  public static Notification createNotification(
      String notificationTitle,
      String notificationContent,
      List<MemberNotification> memberNotifications) {
    Notification notification =
        Notification.builder()
            .notificationTitle(notificationTitle)
            .notificationContent(notificationContent)
            .build();
    notification.addMemberNotifications(memberNotifications);
    return notification;
  }

  private void addMemberNotifications(List<MemberNotification> memberNotifications) {
    this.memberNotifications.addAll(memberNotifications);
    for (MemberNotification memberNotification : memberNotifications) {
      if (memberNotification.getId().getNotification() == null) {
        memberNotification.setNotification(this);
      }
    }
  }
}
