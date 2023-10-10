package com.lotdiz.notificationservice.controller.restcontroller;

import com.lotdiz.notificationservice.dto.response.GetNotificationDetailResponseDto;
import com.lotdiz.notificationservice.service.MemberNotificationService;
import com.lotdiz.notificationservice.service.UnreadNotificationService;
import com.lotdiz.notificationservice.utils.SuccessResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationRestController {
  private final UnreadNotificationService unreadNotificationService;
  private final MemberNotificationService memberNotificationService;

  @GetMapping("/notifications")
  public ResponseEntity<SuccessResponse<Map<String, List<GetNotificationDetailResponseDto>>>>
      getNotificationDetails(
          @RequestHeader(name = "memberId") Long memberId,
          @PageableDefault(
                  page = 0,
                  size = 20,
                  sort = {"createdAt"},
                  direction = Sort.Direction.DESC)
              Pageable pageable) {
    List<GetNotificationDetailResponseDto> getNotificationDetailResponseDtos =
        memberNotificationService.getNotificationDetails(memberId, pageable);

    return ResponseEntity.ok()
        .body(
            SuccessResponse.<Map<String, List<GetNotificationDetailResponseDto>>>builder()
                .code(String.valueOf(HttpStatus.OK.value()))
                .message(HttpStatus.OK.name())
                .detail("알림 조회 성공")
                .data(Map.of("notifications", getNotificationDetailResponseDtos))
                .build());
  }

  @GetMapping("notifications/unread-count")
  public ResponseEntity<SuccessResponse<Map<String, Long>>> numberOfUnreadNotifications() {
    Long memberId = 1L;
    Long numberOfUnreadNotifications =
        unreadNotificationService.getNumberOfUnreadNotifications(memberId);
    return ResponseEntity.ok()
        .body(
            SuccessResponse.<Map<String, Long>>builder()
                .code(String.valueOf(HttpStatus.OK.value()))
                .data(Map.of("unreadNotificationCount", numberOfUnreadNotifications))
                .message(HttpStatus.OK.name())
                .detail("안 읽은 알림 개수")
                .build());
  }
}
