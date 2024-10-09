package stanl_2.weshareyou.domain.notice.Service;

import org.springframework.stereotype.Service;
import stanl_2.weshareyou.domain.notice.aggregate.dto.NoticeDTO;

import java.util.List;

@Service
public interface NoticeService {
//    int createNotice(NoticeDTO noticeCreateRequestDTO, List<String> roles) throws IllegalAccessException;
//    int updateNotice(NoticeDTO noticeUpdateRequestDTO, List<String> roles) throws IllegalAccessException;
//    int deleteNotice(NoticeDTO noticeDeleteRequestDTO, List<String> roles) throws IllegalAccessException;
    NoticeDTO createNotice(NoticeDTO noticeCreateRequestDTO) throws IllegalAccessException;
    Boolean updateNotice(NoticeDTO noticeUpdateRequestDTO) throws IllegalAccessException;
    Boolean deleteNotice(NoticeDTO noticeDeleteRequestDTO) throws IllegalAccessException;
}
