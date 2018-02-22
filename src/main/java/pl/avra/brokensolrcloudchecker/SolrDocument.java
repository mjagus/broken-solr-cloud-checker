package pl.avra.brokensolrcloudchecker;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;

public final class SolrDocument implements Serializable, Cloneable {

    private static final long serialVersionUID = -1850098250642619979L;

    @Id
    @Field(SolrFields.ID)
    private String id;

    @Field(SolrFields.DELETED)
    @Indexed
    private boolean deleted;

    @Field(SolrFields.TYPE)
    @Indexed
    private SolrDocumentType type;

    @Field(SolrFields.USER_ID)
    @Indexed
    private String userId;

    @Field(SolrFields.USER_LOGIN)
    @Indexed
    private String userLogin;

    @Field(SolrFields.TIMESTAMP)
    @Indexed
    private long timestamp;

    public SolrDocument(String id, boolean deleted, SolrDocumentType type, String userId, String userLogin, long timestamp) {
        this.id = id;
        this.deleted = deleted;
        this.type = type;
        this.userId = userId;
        this.userLogin = userLogin;
        this.timestamp = timestamp;
    }

}
