package cl.karangop.flash.models;

/**
 * Created by karan_000 on 10-03-2017.
 */

public class Chat {

    private String id, receiver, photo;
    private boolean notification;
    private long timestamp;

    public Chat() {
    }

    public Chat(String id, String receiver, String photo, boolean notification, long timestamp) {
        this.id = id;
        this.receiver = receiver;
        this.photo = photo;
        this.notification = notification;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

