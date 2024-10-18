package stanl_2.weshareyou.domain.member.service;

import stanl_2.weshareyou.domain.member.aggregate.dto.HistoryDTO;

import java.util.List;

public interface HistoryService {
    List<HistoryDTO> findIp();
}
