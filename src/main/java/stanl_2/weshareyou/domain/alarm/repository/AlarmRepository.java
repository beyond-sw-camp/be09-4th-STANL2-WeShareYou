package stanl_2.weshareyou.domain.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.weshareyou.domain.alarm.aggregate.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
