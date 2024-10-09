package stanl_2.weshareyou.domain.notice.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stanl_2.weshareyou.domain.notice.Service.NoticeService;
import stanl_2.weshareyou.domain.notice.aggregate.dto.NoticeDTO;
import stanl_2.weshareyou.domain.notice.aggregate.vo.request.NoticeCreateRequestVO;
import stanl_2.weshareyou.domain.notice.aggregate.vo.request.NoticeDeleteRequestVO;
import stanl_2.weshareyou.domain.notice.aggregate.vo.request.NoticeUpdateRequestVO;
import stanl_2.weshareyou.domain.notice.aggregate.vo.response.NoticeCreateResponseVO;
import stanl_2.weshareyou.global.common.response.ApiResponse;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/notice")
public class NoticeController {
    private final NoticeService noticeService;
    private final ModelMapper modelMapper;

    @Autowired
    public NoticeController(NoticeService noticeService, ModelMapper modelMapper) {
        this.noticeService = noticeService;
        this.modelMapper = modelMapper;
    }


    /* 설명.
     *  req:
     *   notice_title
     *   notice_content
     *   admin_id(추후 토큰에서 추출)
     *  res:
     *   id
     *   title
    * */
    @PostMapping("")
    private ApiResponse<?> createNotice(@RequestBody NoticeCreateRequestVO noticeCreateRequestVO
//                                                            ,@RequestAttribute("memberId") Long memberId,
//                                                            @RequestAttribute("authorities") List<String> roles
    ) throws IllegalAccessException {

        NoticeDTO noticeCreateRequestDTO = modelMapper.map(noticeCreateRequestVO, NoticeDTO.class);
        NoticeCreateResponseVO noticeCreateResponseVO = modelMapper.map(noticeService.createNotice(noticeCreateRequestDTO), NoticeCreateResponseVO.class);
        return ApiResponse.ok(noticeCreateResponseVO);
    }

    /* 설명.
     *  req:
     *   notice_id
     *   notice_title
     *   notice_content
     *   admin_id(추후 토큰에서 추출)
     *  res:
     *   성공 메시지
    * */

    @PutMapping("")
    private ApiResponse<?> updateNotice(@RequestBody NoticeUpdateRequestVO noticeUpdateRequestVO
    ) throws IllegalAccessException {
        NoticeDTO noticeUpdateRequestDTO = modelMapper.map(noticeUpdateRequestVO, NoticeDTO.class);
        noticeService.updateNotice(noticeUpdateRequestDTO);

        return ApiResponse.ok("update success");
    }

    /* 설명.
     *  req:
     *   notice_id
     *   admin_id(추후 토큰에서 추출)
     *  res:
     *   성공 메시지
     * */
    @DeleteMapping("")
    private ApiResponse<?> deleteNotice(@RequestBody NoticeDeleteRequestVO noticeDeleteRequestVO
    ) throws IllegalAccessException {
        NoticeDTO noticeDeleteRequestDTO = modelMapper.map(noticeDeleteRequestVO, NoticeDTO.class);
        noticeService.deleteNotice(noticeDeleteRequestDTO);

        return ApiResponse.ok("delete success");
    }
}
