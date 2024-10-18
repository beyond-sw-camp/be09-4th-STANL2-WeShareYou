package stanl_2.weshareyou.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import stanl_2.weshareyou.domain.member.aggregate.dto.HistoryDTO;
import stanl_2.weshareyou.domain.member.aggregate.history.LoginHistory;
import stanl_2.weshareyou.domain.member.repository.HistoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service("historyServiceImpl")
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService{

    private final HistoryRepository historyRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<HistoryDTO> findIp() {
        List<LoginHistory> historyRequestDTO = historyRepository.findAll().stream().toList();

        List<HistoryDTO> historyResponseDTO = new ArrayList<>();
        for(LoginHistory history : historyRequestDTO) {
            historyResponseDTO.add(modelMapper.map(history, HistoryDTO.class));
        }

        return historyResponseDTO;
    }
}
