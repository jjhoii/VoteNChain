package ssafy.a306.vnc.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import ssafy.a306.vnc.model.ChartVO;

@Controller
public class WebSocketController {

    @MessageMapping("/socket/chart/{hashcode}/receive")
    @SendTo("/socket/chart/{hashcode}/send")
    public ChartVO chartSocketHandler(@DestinationVariable String hashcode,@Payload ChartVO data){

        return data;
    }
}