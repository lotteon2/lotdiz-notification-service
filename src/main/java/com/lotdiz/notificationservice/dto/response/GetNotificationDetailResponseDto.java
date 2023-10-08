package com.lotdiz.notificationservice.dto.response;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetNotificationDetailResponseDto {

  private Long notificationId;
  private String notificationTitle;
  private String notificationContent;
  private Boolean notificationIsRead;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
