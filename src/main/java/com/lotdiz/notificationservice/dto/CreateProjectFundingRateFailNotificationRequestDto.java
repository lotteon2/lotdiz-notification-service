package com.lotdiz.notificationservice.dto;

import java.util.List;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateProjectFundingRateFailNotificationRequestDto {

  private String projectName;
  private Boolean isSufficientFundingAmount;
  private List<Long> memberIds;
}
