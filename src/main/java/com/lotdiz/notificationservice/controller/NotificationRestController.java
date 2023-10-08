package com.lotdiz.notificationservice.controller;

import com.lotdiz.notificationservice.dto.response.GetNotificationDetailResponseDto;
import com.lotdiz.notificationservice.service.MemberNotificationService;
import java.util.List;
import java.util.Map;

import com.lotdiz.notificationservice.utils.SuccessResponse;
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

  private final MemberNotificationService memberNotificationService;

  @GetMapping("/notifications")
  public ResponseEntity<SuccessResponse> getNotificationDetails(
      @RequestHeader(name = "memberId") Long memberId,
      @PageableDefault(
              page = 0,
              size = 20,
              sort = {"createdAt"},
              direction = Sort.Direction.DESC)
          Pageable pageable) {
    List<GetNotificationDetailResponseDto> getNotificationDetailResponseDtos =
        memberNotificationService.getNotificationDetails(memberId, pageable);

    return ResponseEntity.ok().body(SuccessResponse.builder()
            .code(String.valueOf(HttpStatus.OK.value()))
            .message(HttpStatus.OK.name())
            .detail("알림 조회 성공")
            .data(Map.of("notifications", getNotificationDetailResponseDtos))
            .build());
  }
}