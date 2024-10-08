package stanl_2.weshareyou.domain.member.aggregate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
