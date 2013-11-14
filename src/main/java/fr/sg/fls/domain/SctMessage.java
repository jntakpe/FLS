package fr.sg.fls.domain;

/**
 * @author jntakpe
 */
public class SctMessage {

    private String versionTag;

    private String messageId;

    private String entity;

    public SctMessage(String versionTag, String messageId, String entity) {
        this.versionTag = versionTag;
        this.messageId = messageId;
        this.entity = entity;
    }

    public String getVersionTag() {
        return versionTag;
    }

    public void setVersionTag(String versionTag) {
        this.versionTag = versionTag;
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
