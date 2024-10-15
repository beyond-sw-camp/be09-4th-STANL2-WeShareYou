package stanl_2.weshareyou.domain.notice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.weshareyou.domain.notice.aggregate.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Slice<Notice> findByIdLessThanOrderByCreatedAtDesc(Long cursorId, Pageable pageable);
    Slice<Notice> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
