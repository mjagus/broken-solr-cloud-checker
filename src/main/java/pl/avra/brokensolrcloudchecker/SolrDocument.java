package pl.avra.brokensolrcloudchecker;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;

public final class SolrDocument implements Serializable, Cloneable {

    private static final long serialVersionUID = -1850098250642619979L;

    /**
     * Tekstowa wartość identyfikatora biznesowego encji/agregatu z którego utworzono ten dokument. O typie decyduje zawartość pola {@link #type} .
     */
    @Id
    @Field(SolrFields.ID)
    private String id;

    /**
     * Czy ten dokument jest oznaczony jako skasowany?
     */
    @Field(SolrFields.DELETED)
    @Indexed
    private boolean deleted;

    /**
     * Typ dokumentu. Na jego podstawie wiemy z jakiej encji/agregatu został ten dokument utworzony.
     */
    @Field(SolrFields.TYPE)
    @Indexed
    private SolrDocumentType type;

    /**
     * Znacznik czasowy. Moment utworzenia encji powiązanej z tym dokumentem.
     */
    @Field(SolrFields.TIMESTAMP)
    @Indexed
    private long timestamp;

    public SolrDocument(String id, boolean deleted, SolrDocumentType type, long timestamp) {
        this.id = id;
        this.deleted = deleted;
        this.type = type;
        this.timestamp = timestamp;
    }

}
