package stanl_2.weshareyou.domain.notice.Service;

import org.springframework.stereotype.Service;
import stanl_2.weshareyou.domain.notice.aggregate.dto.NoticeDTO;
import stanl_2.weshareyou.domain.notice.aggregate.vo.response.NoticeReadAllResponseVO;
import stanl_2.weshareyou.domain.notice.aggregate.vo.response.NoticeReadByIdResponseVO;

import java.util.List;

@Service
public interface NoticeService {

    List<NoticeDTO> readAllNotices();
    NoticeDTO readNoticeById(Long noticeId);
    NoticeDTO createNotice(NoticeDTO noticeCreateRequestDTO) throws IllegalAccessException;
    Boolean updateNotice(NoticeDTO noticeUpdateRequestDTO) throws IllegalAccessException;
    Boolean deleteNotice(NoticeDTO noticeDeleteRequestDTO) throws IllegalAccessException;
}
