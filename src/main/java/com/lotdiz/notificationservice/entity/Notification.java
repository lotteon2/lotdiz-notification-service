package com.lotdiz.notificationservice.entity;

import com.lotdiz.notificationservice.entity.common.BaseEntity;
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
public class Notification extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "notification_id")
  private Long id;

  @OneToMany(
      mappedBy = "id.notification",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      orphanRemoval = true)
  private List<MemberNotification> memberNotifications;

  @Column(name = "notification_title", nullable = false)
  private String notificationTitle;

  @Column(name = "notification_content", nullable = false)
  private String notificationContent;
}
