package pl.avra.brokensolrcloudchecker;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.LBHttpSolrClient;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

@Configuration
class SolrConfiguration {

    @Value("${solr.default-collection}")
    private String defaultCollection;

    @Value("${solr.zk-host}")
    private List<String> zkHost;

    @Bean
    SolrClient solrClient() {
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set(HttpClientUtil.PROP_USE_RETRY, false);

        HttpClient client = HttpClientUtil.createClient(params);
        LBHttpSolrClient lbClient = new LBHttpSolrClient.Builder().withHttpClient(client).build();

        CloudSolrClient cloudClient = new CloudSolrClient.Builder().withZkHost(zkHost).withLBHttpSolrClient(lbClient).build();
        cloudClient.setDefaultCollection(defaultCollection);
        return cloudClient;
    }

    @Bean
    SolrTemplate solrTemplate() {
        return new SolrTemplate(solrClient());
    }

}
