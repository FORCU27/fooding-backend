package im.fooding.core.global.util;

import org.springframework.data.elasticsearch.annotations.Document;

public class ElasticsearchUtil {
    public static String getIndexName(Class<?> clazz) {
        Document docAnnotation = clazz.getAnnotation(Document.class);
        if (docAnnotation != null) {
            return docAnnotation.indexName();
        }
        throw new IllegalArgumentException("해당 클래스에 @Document 애노테이션이 없습니다.");
    }
}
