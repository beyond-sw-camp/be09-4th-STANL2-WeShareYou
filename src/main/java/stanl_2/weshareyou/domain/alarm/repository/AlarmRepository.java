package stanl_2.weshareyou.domain.alarm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.weshareyou.domain.alarm.aggregate.dto.AlarmDTO;
import stanl_2.weshareyou.domain.alarm.aggregate.entity.Alarm;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Page<Alarm> findByMemberIdOrderByCreatedAtDesc(Member member, Pageable pageable);

    Slice<Alarm> findByMemberIdAndIdLessThanOrderByCreatedAtDesc(Member member, Long cursorId, Pageable pageable);
}
