package im.fooding.app.controller.test;

import im.fooding.core.model.test.TestDocument;
import im.fooding.core.repository.test.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// todo: PR 확인 후 삭제
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestRepository testRepository;

    @GetMapping
    public List<TestDocument> getAll() {
        return testRepository.findAll();
    }

    @PostMapping
    public void create(@RequestBody TestDocument test) {
        testRepository.save(test);
    }
}
