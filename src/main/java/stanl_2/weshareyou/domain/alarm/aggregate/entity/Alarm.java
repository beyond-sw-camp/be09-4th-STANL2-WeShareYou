package stanl_2.weshareyou.domain.alarm.aggregate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

import java.sql.Timestamp;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "ALARM")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALARM_ID")
    private Long id;

    @Column(name = "ALARM_MESSAGE")
    @NotNull
    private String message;

    @Column(name = "ALARM_URL")
    @NotNull
    private String url;

    @Column(name = "ALARM_TYPE")
    @Enumerated(EnumType.STRING)
    @NotNull
    private AlarmType alarmType;

    @Column(name = "ALARM_READ_STATUS")
    private Boolean readStatus;

    @Column(name = "ALARM_CREATED_AT")
    @NotNull
    private Timestamp createdAt;

    @Column(name = "ALARM_SENDER")
    @NotNull
    private String sender;

    @Column(name = "ALARM_SENDER")
    @NotNull
    private String sender;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    @NotNull
    private Member memberId;

}
