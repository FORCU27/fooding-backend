package im.fooding.app.controller.test;

import im.fooding.app.service.test.TestService;
import im.fooding.core.model.test.TestDocument;
import im.fooding.core.repository.test.TestRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// todo: PR 확인 후 삭제
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestRepository testRepository;
    private final TestService testService;

    @GetMapping
    public List<TestDocument> getAll() {
        return testRepository.findAll();
    }

    @PostMapping
    public void create(@RequestBody TestDocument test) {
        testRepository.save(test);
    }

    @PostMapping("/store/create/{count}")
    @Operation( summary = "테스트용 더미 가게 생성 API ( Stage에서 사용 금지 )" )
    public void createDummyStoreToTest(
            @PathVariable int count
    ){

    }
}
