package fr.sg.fls.domain;

import fr.sg.fmk.domain.GenericDomain;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import java.util.Date;

/**
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_SCT_MESSAGE")
public class SctMessage extends GenericDomain {

    private String messageId;

    private String entity;

    private String IOIndicator;

    private String senderReference;

    private String amount;

    private String appCode;

    private String sender;

    private String receiver;

    private Date filterDate;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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
