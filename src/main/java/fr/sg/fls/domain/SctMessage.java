package fr.sg.fls.domain;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * POJO contenant les informations à persister récupérées dans le message SCT.
 *
 * @author jntakpe
 */
@Entity
@Table(name = "sct_message", uniqueConstraints = @UniqueConstraint(columnNames = {"messageId", "entity"}))
@SequenceGenerator(name = "SG", sequenceName = "SEQ_SCT_MESSAGE")
public class SctMessage {

    @Id
    @GeneratedValue(generator = "SG", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Version
    private Integer version;

    @Column(nullable = false)
    @NotEmpty
    private String messageId;

    @Column(nullable = false)
    @NotEmpty
    private String entity;

    @Column(nullable = false)
    @NotEmpty
    private String IOIndicator;

    private String senderReference;

    private BigDecimal amount;

    @Column(nullable = false)
    @NotEmpty
    private String appCode;

    @Column(nullable = false)
    @NotEmpty
    private String sender;

    @Column(nullable = false)
    @NotEmpty
    private String receiver;

    @Column(nullable = false)
    @NotNull
    private Date filterDate;

    @Transient
    private String jmsMsgId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getIOIndicator() {
        return IOIndicator;
    }

    public void setIOIndicator(String IOIndicator) {
        this.IOIndicator = IOIndicator;
    }

    public String getSenderReference() {
        return senderReference;
    }

    public void setSenderReference(String senderReference) {
        this.senderReference = senderReference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getFilterDate() {
        return filterDate;
    }

    public void setFilterDate(Date filterDate) {
        this.filterDate = filterDate;
    }

    public String getJmsMsgId() {
        return jmsMsgId;
    }

    public void setJmsMsgId(String jmsMsgId) {
        this.jmsMsgId = jmsMsgId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SctMessage that = (SctMessage) o;

        if (entity != null ? !entity.equals(that.entity) : that.entity != null) return false;
        if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageId != null ? messageId.hashCode() : 0;
        result = 31 * result + (entity != null ? entity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SctMessage{" +
                "messageId='" + messageId + '\'' +
                '}';
    }
}
