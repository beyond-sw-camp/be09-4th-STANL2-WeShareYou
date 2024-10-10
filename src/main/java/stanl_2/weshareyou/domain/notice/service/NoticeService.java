package stanl_2.weshareyou.domain.notice.service;

import org.springframework.stereotype.Service;
import stanl_2.weshareyou.domain.notice.aggregate.dto.NoticeDTO;

import java.util.List;

@Service
public interface NoticeService {

    List<NoticeDTO> readAllNotices();
    NoticeDTO readNoticeById(Long noticeId);
    NoticeDTO createNotice(NoticeDTO noticeCreateRequestDTO);
    Boolean updateNotice(NoticeDTO noticeUpdateRequestDTO);
    Boolean deleteNotice(NoticeDTO noticeDeleteRequestDTO);
}
