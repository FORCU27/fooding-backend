package im.fooding.core.repository.test;

import im.fooding.core.model.test.TestDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

// todo: PR 확인 후 삭제
public interface TestRepository extends MongoRepository<TestDocument, String> {
}
