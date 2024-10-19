package stanl_2.weshareyou.domain.notice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.member.repository.MemberRepository;
import stanl_2.weshareyou.domain.notice.aggregate.dto.NoticeDTO;
import stanl_2.weshareyou.domain.notice.aggregate.entity.Notice;
import stanl_2.weshareyou.domain.notice.repository.NoticeRepository;
import stanl_2.weshareyou.global.common.dto.CursorDTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class NoticeServiceImplTests {

    @MockBean
    private NoticeRepository noticeRepository;

    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NoticeServiceImpl noticeService;

    @BeforeEach
    void setUp() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Test
    void testReadNoticeById() {
        // given
        Long noticeId = 1L;
        Member admin = new Member();
        admin.setId(1L);

        Notice notice = new Notice();
        notice.setId(noticeId);
        notice.setTitle("Sample Notice");
        notice.setContent("Sample Content"); // 내용도 추가
        notice.setMember(admin); // admin을 notice에 설정
        notice.setCreatedAt(new Timestamp(System.currentTimeMillis())); // 생성 시간 설정
        notice.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // 업데이트 시간 설정

        when(noticeRepository.findById(noticeId)).thenReturn(Optional.of(notice));

        // when
        NoticeDTO result = noticeService.readNoticeById(noticeId);

        // then
        assertNotNull(result);
        assertEquals(noticeId, result.getId());
        assertEquals("Sample Notice", result.getTitle());
        assertEquals("Sample Content", result.getContent()); // 내용도 확인
        verify(noticeRepository, times(1)).findById(noticeId);
    }

    @Test
    void testCreateNotice() {
        // given
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setTitle("New Notice");
        noticeDTO.setContent("New Notice Content");
        noticeDTO.setAdminId(1L);

        Member admin = new Member();
        admin.setId(1L);

        // Notice 객체를 직접 생성
        Notice notice = new Notice();
        notice.setId(1L);
        notice.setTitle(noticeDTO.getTitle()); // Title 설정
        notice.setContent(noticeDTO.getContent()); // Content 설정
        notice.setMember(admin); // Admin 설정
        notice.setCreatedAt(new Timestamp(System.currentTimeMillis())); // 생성 시간 설정
        notice.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // 업데이트 시간 설정
        notice.setActive(true); // 기본값으로 Active 설정

        // Mock 설정
        when(memberRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(noticeRepository.save(any(Notice.class))).thenReturn(notice);

        // when
        NoticeDTO result = noticeService.createNotice(noticeDTO);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Notice", result.getTitle());
        verify(memberRepository, times(1)).findById(1L);
        verify(noticeRepository, times(1)).save(any(Notice.class));
    }


}
