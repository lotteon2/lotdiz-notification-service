package com.lotdiz.notificationservice.controller.restcontroller;

import com.lotdiz.notificationservice.service.UnreadNotificationService;
import com.lotdiz.notificationservice.utils.SuccessResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NotificationRestController {
  private final UnreadNotificationService unreadNotificationService;

  @GetMapping("notifications/unread-count")
  public ResponseEntity<SuccessResponse> numberOfUnreadNotifications() {
    Long memberId = 1L;
    Long numberOfUnreadNotifications =
        unreadNotificationService.numberOfUnreadNotifications(memberId);
    return ResponseEntity.ok()
        .body(
            SuccessResponse.builder()
                .code(String.valueOf(HttpStatus.OK.value()))
                .data(Map.of("unreadNotificationCount", numberOfUnreadNotifications))
                .message(HttpStatus.OK.name())
                .detail("안 읽은 알림 개수")
                .build());
  }
}
