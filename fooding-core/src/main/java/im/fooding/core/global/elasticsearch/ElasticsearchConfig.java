package im.fooding.core.global.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
@Getter
public class ElasticsearchConfig {
    private final String host;
    private final String key;

    public ElasticsearchConfig(@Value("${spring.elasticsearch.uris}") String host,
                               @Value("${spring.elasticsearch.key}") String key) {
        this.host = host;
        this.key = key;
    }

    @Bean
    public RestClient restClient() {
        Header apiKeyHeader = new BasicHeader("Authorization", "ApiKey %s".formatted(key));
        return RestClient.builder(HttpHost.create(host))
                .setDefaultHeaders(new Header[]{apiKeyHeader})
                .build();
    }

    @Bean
    public ElasticsearchClient elasticsearchClient(RestClient restClient) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper(mapper));
        return new ElasticsearchClient(transport);
    }
}
