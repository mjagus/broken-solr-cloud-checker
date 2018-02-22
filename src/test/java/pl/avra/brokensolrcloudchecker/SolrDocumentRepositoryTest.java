package pl.avra.brokensolrcloudchecker;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SolrDocumentRepositoryTest {

    private static final int ALL_DOCUMENTS = 10000;

    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private SolrDocumentRepository repository;

    @Value("${solr.default-collection}")
    private String defaultCollection;

    @Test
    public void allAddedDocumentsShouldExistInTheCloud() {
        long timestamp = System.currentTimeMillis();

        for(int i = 0; i < ALL_DOCUMENTS; i++) {
            SolrDocument document = givenDocument(timestamp + i);

            solrTemplate.saveBean(defaultCollection, document, Duration.ofMinutes(5));
            solrTemplate.softCommit(defaultCollection);
        }

        for(int i = 0; i < ALL_DOCUMENTS; i++) {
            String id = String.valueOf(timestamp + i);
            assertThat(repository.findById(id)).as("repository.findById(%s)", id).isPresent();
        }
    }

    private SolrDocument givenDocument(long timestamp) {
        return new SolrDocument(String.valueOf(timestamp), false, SolrDocumentType.USER, "user" + timestamp, timestamp);
    }

}
