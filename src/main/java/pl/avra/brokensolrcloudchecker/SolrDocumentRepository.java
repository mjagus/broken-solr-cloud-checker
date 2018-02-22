package pl.avra.brokensolrcloudchecker;

import org.springframework.data.solr.repository.SolrCrudRepository;

interface SolrDocumentRepository extends SolrCrudRepository<SolrDocument, String> {

}
