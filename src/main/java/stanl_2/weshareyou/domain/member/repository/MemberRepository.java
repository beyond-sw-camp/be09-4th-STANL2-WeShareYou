package stanl_2.weshareyou.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.weshareyou.domain.member.aggregate.dto.reponse.reponseMemberDetailDTO;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByloginId(String loginId);

    Optional<reponseMemberDetailDTO> findByLoginId(String loginId);
}
