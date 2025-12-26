package im.fooding.app.controller.test;

import im.fooding.app.service.auth.AuthService;
import im.fooding.core.model.test.TestDocument;
import im.fooding.core.repository.test.TestRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

// todo: PR 확인 후 삭제
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestRepository testRepository;
    private final AuthService authService;

    @GetMapping
    public List<TestDocument> getAll() {
        return testRepository.findAll();
    }

    @PostMapping
    public void create(@RequestBody TestDocument test) {
        testRepository.save(test);
    }

//    @PostMapping("/email" )
//    public void testEmail( @RequestParam String email, @RequestParam String name ){
//        LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(30);
//        String expiredAtStr = "%d.%02d.%02d %s %d:%02d".formatted(
//                expiredAt.getYear(),
//                expiredAt.getMonthValue(),
//                expiredAt.getDayOfMonth(),
//                expiredAt.getHour() < 12 ? "오전" : "오후",
//                expiredAt.getHour() % 12 == 0 ? 12 : expiredAt.getHour() % 12,
//                expiredAt.getMinute()
//        );
//        authService.sendEmail( name, email, "test-url-encoded", expiredAtStr );
//    }
}
