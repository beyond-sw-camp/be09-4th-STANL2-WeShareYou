package stanl_2.weshareyou.domain.notice.service;

import org.springframework.stereotype.Service;
import stanl_2.weshareyou.domain.notice.aggregate.dto.NoticeDTO;
import stanl_2.weshareyou.global.common.dto.CursorDTO;

@Service
public interface NoticeService {

    CursorDTO readNotices(CursorDTO cursorDTO);
    NoticeDTO readNoticeById(Long noticeId);
    NoticeDTO createNotice(NoticeDTO noticeCreateRequestDTO);
    Boolean updateNotice(NoticeDTO noticeUpdateRequestDTO);
    Boolean deleteNotice(NoticeDTO noticeDeleteRequestDTO);
}
