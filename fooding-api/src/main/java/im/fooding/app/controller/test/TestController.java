package im.fooding.app.controller.test;

import im.fooding.core.global.kafka.KafkaProducer;
import im.fooding.core.model.test.TestDocument;
import im.fooding.core.repository.test.TestRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// todo: PR 확인 후 삭제
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestRepository testRepository;
    private final KafkaProducer kafkaProducer;

    @GetMapping
    public List<TestDocument> getAll() {
        return testRepository.findAll();
    }

    @PostMapping
    public void create(@RequestBody TestDocument test) {
        testRepository.save(test);
    }

    @PostMapping("/kafka")
    public void sendMessage() {
        kafkaProducer.sendMessage("test-topic", "test-message");
    }
}
