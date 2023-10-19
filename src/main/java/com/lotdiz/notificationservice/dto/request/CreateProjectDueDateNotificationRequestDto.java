package com.lotdiz.notificationservice.dto.request;

import java.util.List;
import lombok.*;

@Getter
@Builder
public class CreateProjectDueDateNotificationRequestDto {

  private Long projectId;
  private String projectName;
  private Long makerMemberId;
  private Boolean isTargetAmountExceed;
  private List<Long> memberIds;
}
