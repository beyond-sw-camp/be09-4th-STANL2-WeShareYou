package stanl_2.weshareyou.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.weshareyou.domain.notice.aggregate.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
