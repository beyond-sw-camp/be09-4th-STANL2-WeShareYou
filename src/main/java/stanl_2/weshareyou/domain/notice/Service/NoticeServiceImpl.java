package stanl_2.weshareyou.domain.notice.Service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.member.repository.MemberRepository;
import stanl_2.weshareyou.domain.notice.aggregate.dto.NoticeDTO;
import stanl_2.weshareyou.domain.notice.aggregate.entity.Notice;
import stanl_2.weshareyou.domain.notice.repository.NoticeRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService{
    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;
    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT);

    @Autowired
    public NoticeServiceImpl(NoticeRepository noticeRepository, ModelMapper modelMapper, MemberRepository memberRepository) {
        this.noticeRepository = noticeRepository;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.modelMapper = modelMapper;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public List<NoticeDTO> readAllNotices() {
        List<Notice> noticeList = noticeRepository.findAll();


        List<NoticeDTO> noticeDTOList = noticeList.stream()
                .map(notice -> {
                    NoticeDTO dto = new NoticeDTO();
                    dto.setId(notice.getId());
                    dto.setTitle(notice.getTitle());
                    dto.setCreatedAt(notice.getCreatedAt());
                    dto.setAdminId(notice.getAdminId().getId());
                    return dto;
                })
                .toList();

        return noticeDTOList;
    }

    @Override
    @Transactional
    public NoticeDTO readNoticeById(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElse(null);

        /* 설명. @ManyToOne 관계일 때, dto에 엔티티 타입 제외하면 편해질 수 있다. (따로 setter) */

        NoticeDTO noticeReadByIdResponseDTO = new NoticeDTO();

        noticeReadByIdResponseDTO.setId(notice.getId());
        noticeReadByIdResponseDTO.setTitle(notice.getTitle());
        noticeReadByIdResponseDTO.setContent(notice.getContent());
        noticeReadByIdResponseDTO.setCreatedAt(notice.getCreatedAt());
        noticeReadByIdResponseDTO.setUpdatedAt(notice.getUpdatedAt());
        noticeReadByIdResponseDTO.setActive(notice.getActive());
        noticeReadByIdResponseDTO.setAdminId(notice.getAdminId().getId());

        return noticeReadByIdResponseDTO;
    }

    @Override
    @Transactional
    public NoticeDTO createNotice(NoticeDTO noticeCreateRequestDTO){
        Member admin = memberRepository.findById(noticeCreateRequestDTO.getAdminId())
                .orElseThrow(() -> new NoSuchElementException("해당 관리자 계정이 존재하지 않습니다."));

        Notice notice = modelMapper.map(noticeCreateRequestDTO, Notice.class);

        notice.setAdminId(admin);

        notice.setCreatedAt(LocalDateTime.now().format(FORMATTER));
        notice.setUpdatedAt(LocalDateTime.now().format(FORMATTER));
        notice.setActive(true);

        Notice savedNotice = noticeRepository.save(notice);

        if (savedNotice == null || savedNotice.getId() == null) {
            throw new IllegalStateException("공지사항 생성에 실패했습니다.");
        }
        NoticeDTO noticeCreateResponseDTO = new NoticeDTO();

        noticeCreateResponseDTO.setId(savedNotice.getId());
        noticeCreateResponseDTO.setTitle(savedNotice.getTitle());


        return noticeCreateResponseDTO;
}

    @Override
    @Transactional
    public Boolean updateNotice(NoticeDTO noticeUpdateRequestDTO)
    {
        Notice notice = noticeRepository.findById(noticeUpdateRequestDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 공지가 존재하지 않습니다."));


        notice.setTitle(noticeUpdateRequestDTO.getTitle());
        notice.setContent(noticeUpdateRequestDTO.getContent());
        notice.setUpdatedAt(LocalDateTime.now().format(FORMATTER));

        Notice updatedNotice = noticeRepository.save(notice);

        if (updatedNotice != null && !updatedNotice.getId().equals(noticeUpdateRequestDTO.getId())) {
            throw new IllegalStateException("공지사항 비활성화 실패");
        }

        return true;
    }

    @Override
    @Transactional
    public Boolean deleteNotice(NoticeDTO noticeDeleteRequestDTO) {

        Notice notice = noticeRepository.findById(noticeDeleteRequestDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 공지가 존재하지 않습니다."));

        notice.setActive(false);
        notice.setUpdatedAt(LocalDateTime.now().format(FORMATTER));

        Notice deleteNotice = noticeRepository.save(notice);

        if (deleteNotice != null && !deleteNotice.getId().equals(noticeDeleteRequestDTO.getId())) {
            throw new IllegalStateException("공지사항 비활성화 실패");
        }

        return true;
    }

}
