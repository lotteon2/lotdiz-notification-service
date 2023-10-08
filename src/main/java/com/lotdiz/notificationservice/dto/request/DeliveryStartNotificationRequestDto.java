package com.lotdiz.notificationservice.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryStartNotificationRequestDto {
  private Long fundingId;
  private Long memberId;
  private String memberName;
  private String projectName;
}
