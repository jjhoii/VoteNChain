package ssafy.a306.vnc.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssafy.a306.vnc.enums.MessageType;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatVO {

    private MessageType type;
    private String content;
    private String sender;
}