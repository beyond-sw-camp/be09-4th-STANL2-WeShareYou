package stanl_2.weshareyou.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import stanl_2.weshareyou.domain.member.aggregate.dto.HistoryDTO;
import stanl_2.weshareyou.domain.member.service.HistoryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final HistoryService historyService;

    @GetMapping("/ip")
    public String getLoginHistory(Model model) {
        List<HistoryDTO> historyList = historyService.findIp();
        System.out.println(historyList);
        model.addAttribute("historyList", historyList);  // 모델에 데이터 추가
        return "login-history";  // ip.html 템플릿 반환
    }
}

