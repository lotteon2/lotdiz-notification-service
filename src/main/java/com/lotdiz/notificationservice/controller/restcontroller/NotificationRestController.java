package com.lotdiz.notificationservice.controller.restcontroller;

import com.lotdiz.notificationservice.dto.response.GetNotificationResponseDto;
import com.lotdiz.notificationservice.entity.MemberNotification;
import com.lotdiz.notificationservice.mapper.MemberNotificationCustomMapper;
import com.lotdiz.notificationservice.service.MemberNotificationService;
import com.lotdiz.notificationservice.service.UnreadNotificationService;
import com.lotdiz.notificationservice.utils.SuccessResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationRestController {

  private final MemberNotificationService memberNotificationService;
  private final UnreadNotificationService unreadNotificationService;

  private final MemberNotificationCustomMapper memberNotificationCustomMapper;

  @GetMapping("/notifications")
  public ResponseEntity<SuccessResponse<Map<String, Object>>> getNotificationDetails(
      @RequestHeader(name = "memberId") Long memberId,
      @PageableDefault(
              page = 0,
              size = 5,
              sort = {"createdAt"},
              direction = Sort.Direction.DESC)
          Pageable pageable) {
    Page<MemberNotification> memberNotifications =
        memberNotificationService.getMemberNotifications(memberId, pageable);
    int totalPages = memberNotifications.getTotalPages();
    List<GetNotificationResponseDto> getNotificationResponseDtos =
        memberNotificationCustomMapper.memberNotificationsToGetNotificationResponseDtos(
            memberNotifications.getContent());

    return ResponseEntity.ok()
        .body(
            SuccessResponse.<Map<String, Object>>builder()
                .code(String.valueOf(HttpStatus.OK.value()))
                .message(HttpStatus.OK.name())
                .detail("알림 조회 성공")
                .data(
                    Map.of("totalPages", totalPages, "notifications", getNotificationResponseDtos))
                .build());
  }

  @GetMapping("/notifications/unread-count")
  public ResponseEntity<SuccessResponse<Map<String, Long>>> numberOfUnreadNotifications(
      @RequestHeader Long memberId) {
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
