package com.lotdiz.notificationservice.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetNotificationReadRequestDto {

    private List<Long> notificationIds;
}
