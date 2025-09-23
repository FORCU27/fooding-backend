package im.fooding.core.service.keyword;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import im.fooding.core.event.keyword.SearchKeywordSavedEvent;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.kafka.KafkaEventHandler;
import im.fooding.core.model.keyword.SearchKeyword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchKeywordService {
    private final ElasticsearchClient client;

    public void save(String keyword) {
        try {
            GetRequest getRequest = GetRequest.of(g -> g
                    .index("search_keywords_v1")
                    .id(keyword)
            );
            GetResponse<SearchKeyword> getResponse = client.get(getRequest, SearchKeyword.class);
            if (getResponse.found()) {
                SearchKeyword searchKeyword = getResponse.source();
                searchKeyword.increaseCount();

                UpdateRequest<SearchKeyword, SearchKeyword> updateRequest = UpdateRequest.of(u -> u
                        .index(searchKeyword.getIndex())
                        .id(searchKeyword.getKeyword())
                        .doc(searchKeyword)
                );

                client.update(updateRequest, SearchKeyword.class);
            } else {
                // 신규생성
                SearchKeyword searchKeyword = new SearchKeyword(keyword);
                client.index(i -> i
                        .index(searchKeyword.getIndex())
                        .id(searchKeyword.getKeyword().toString())
                        .document(searchKeyword)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ErrorCode.ELASTICSEARCH_SAVE_FAILED);
        }
    }

    public List<String> recommendKeywords(String keyword) {
        try {
            SearchResponse<Map> response = client.search(s -> s
                            .index("search_keywords_v1")
                            .query(q -> q
                                    .prefix(p -> p
                                            .field("keyword")
                                            .value(keyword)
                                    )
                            )
                            .sort(sort -> sort
                                    .field(f -> f
                                            .field("count")
                                            .order(SortOrder.Desc)
                                    )
                            )
                            .size(20),
                    Map.class
            );

            return response.hits().hits().stream()
                    .map(hit -> (String) hit.source().get("keyword"))
                    .toList();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ErrorCode.ELASTICSEARCH_SEARCH_FAILED);
        }
    }

    @KafkaEventHandler(SearchKeywordSavedEvent.class)
    public void handleSearchKeywordSavedEvent(SearchKeywordSavedEvent event) {
        try {
            if (!StringUtils.hasText(event.getKeyword())) {
                return;
            }
            String keyword = event.getKeyword().strip();
            keyword = keyword.replaceAll("[^가-힣a-zA-Z0-9 ]", "");
            keyword = keyword.replaceAll("[ㄱ-ㅎㅏ-ㅣ]+", "");
            keyword = keyword.replaceAll("\\s+", " ");
            keyword = keyword.toLowerCase();
            if (keyword.length() > 1) {
                save(keyword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
