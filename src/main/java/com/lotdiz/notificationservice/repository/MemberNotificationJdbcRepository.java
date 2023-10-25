package com.lotdiz.notificationservice.repository;

import com.lotdiz.notificationservice.entity.MemberNotification;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberNotificationJdbcRepository {

  private final JdbcTemplate jdbcTemplate;

  public void batchInsert(List<MemberNotification> memberNotifications) {
    String sql =
        "INSERT INTO member_notification ("
            + "notification_id, member_id, member_notification_is_read, created_at, updated_at"
            + ") VALUES (?, ?, ?, ?, ?)";
    jdbcTemplate.batchUpdate(
        sql,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            MemberNotification memberNotification = memberNotifications.get(i);
            ps.setLong(1, memberNotification.getId().getNotification().getId());
            ps.setLong(2, memberNotification.getId().getMemberId());
            ps.setBoolean(3, memberNotification.getMemberNotificationIsRead());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
          }

          @Override
          public int getBatchSize() {
            return memberNotifications.size();
          }
        });
  }
}
