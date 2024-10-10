package stanl_2.weshareyou.domain.alarm.aggregate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

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
    private AlatmType alatmType;

    @Column(name = "ALARM_READ_STATUS")
    @NotNull
    private Boolean readStatus;

    @Column(name = "ALARM_CREATE_AT")
    @NotNull
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    @NotNull
    private Member memberId;

}
