package xyz.maywr.arsie.api.notifications;

public class Notification {

    private NotificationType type;
    private String title, message;

    public Notification(NotificationType type, String title, String message){
            this.type = type;
            this.title = title;
            this.message = message;
    }




}
