package ua.wholesale.web.site.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class MessageModel {

    private String message;
    private String fromLogin;


    @Override
    public String toString() {
        return "MessageModel{" +
                "message='" + message + '\'' +
                ", fromLogin='" + fromLogin + '\'' +
                '}';
    }
}
