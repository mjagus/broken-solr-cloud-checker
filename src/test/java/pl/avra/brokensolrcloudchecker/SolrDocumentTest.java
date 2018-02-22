package pl.avra.brokensolrcloudchecker;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SolrDocumentTest {

    private static final int ALL_DOCUMENTS = 5000;

    @Autowired
    private SolrTemplate solrTemplate;

    @Value("${solr.default-collection}")
    private String defaultCollection;

    @Test
    public void allAddedDocumentsShouldExistInTheCloud() {
        long timestamp = System.currentTimeMillis();

        for(int i = 0; i < ALL_DOCUMENTS; i++) {
            System.out.println("Progress: " + (i + 1) + "/" + ALL_DOCUMENTS);

            SolrDocument document = givenDocument(timestamp + i);

            solrTemplate.saveBean(defaultCollection, document, Duration.ofMinutes(5));
            solrTemplate.softCommit(defaultCollection);
        }

        for(int i = 0; i < ALL_DOCUMENTS; i++) {
            System.out.println("Verification: " + (i + 1) + "/" + ALL_DOCUMENTS);

            String id = String.valueOf(timestamp + i);
            Query query = new SimpleQuery("id:" + id);
            assertThat(solrTemplate.queryForObject(defaultCollection, query, SolrDocument.class)).as("query(%s)", id).isPresent();
        }
    }

    private SolrDocument givenDocument(long timestamp) {
        return new SolrDocument(String.valueOf(timestamp), false, SolrDocumentType.USER, "userId" + timestamp, "userLogin" + timestamp, timestamp);
    }

}
