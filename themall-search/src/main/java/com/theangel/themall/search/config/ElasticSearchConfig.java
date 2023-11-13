package com.theangel.themall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author poo0054
 */
@Configuration
public class ElasticSearchConfig {

    public static final RequestOptions COMMON_OPTIONS;

    @Value("${spring.elasticsearch.cluster.nodes}")
    private String[] urisCloud;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
    /*    builder.addHeader("Authorization", "Bearer " + TOKEN);
        builder.setHttpAsyncResponseConsumerFactory(
                new HttpAsyncResponseConsumerFactory
                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));*/
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient esClient() {
        // 创建多个HttpHost
        HttpHost[] httpHosts = Arrays.stream(urisCloud).map(HttpHost::create).toArray(HttpHost[]::new);
        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }

}
