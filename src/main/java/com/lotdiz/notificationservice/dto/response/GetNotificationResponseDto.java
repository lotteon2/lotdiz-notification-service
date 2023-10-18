package com.lotdiz.notificationservice.dto.response;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Builder
public class GetNotificationResponseDto {

  private Long notificationId;
  private String notificationTitle;
  private String notificationContent;
  private Boolean notificationIsRead;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
