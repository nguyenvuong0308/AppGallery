package gallery.vnm.com.appgallery.model.local;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MessageComment extends RealmObject {
    @PrimaryKey
    private long id;

    private String textClientId;

    private String messageComment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTextClientId() {
        return textClientId;
    }

    public void setTextClientId(String textClientId) {
        this.textClientId = textClientId;
    }

    public String getMessageComment() {
        return messageComment;
    }

    public void setMessageComment(String messageComment) {
        this.messageComment = messageComment;
    }
}
