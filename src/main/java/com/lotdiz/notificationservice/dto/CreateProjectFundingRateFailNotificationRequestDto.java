package com.lotdiz.notificationservice.dto;

import java.util.List;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateProjectFundingRateFailNotificationRequestDto {

  private Long projectId;
  private String projectName;
  private Long makerMemberId;
  private Boolean isTargetAmountExceed;
  private List<Long> memberIds;
}
