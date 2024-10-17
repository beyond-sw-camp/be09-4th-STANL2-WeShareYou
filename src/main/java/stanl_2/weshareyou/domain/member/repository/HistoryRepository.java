package stanl_2.weshareyou.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.weshareyou.domain.member.aggregate.history.LoginHistory;

public interface HistoryRepository extends JpaRepository<LoginHistory, Long> {
}
