package t;

import com.alibaba.fastjson.JSON;
import com.themall.search.ThemallSearchApplication;
import com.themall.search.config.ElasticSearchConfig;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThemallSearchApplication.class)
public class test {
    @Autowired
    RestHighLevelClient esClient;

    @Autowired
    ElasticSearchConfig elasticSearchConfig;

    @Test
    public void t() {
        System.out.println(esClient);
        System.out.println(elasticSearchConfig.esClient());

    }

    /**
     * indexApi
     * 新增和更新都可以
     */
    @Test
    public void indexApi() throws IOException {
        IndexRequest indexRequest = new IndexRequest("users");
        indexRequest.id("4399");
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name", "胡晶晶");
        objectObjectHashMap.put("age", "3");
        String s = JSON.toJSONString(objectObjectHashMap);
        indexRequest.source(s, XContentType.JSON);
        IndexResponse index = esClient.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(index);
    }

    @Test
    /**
     * 复杂检索
     */
    public void query() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("users");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchRequest.source(searchSourceBuilder);


        esClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
    }
}
