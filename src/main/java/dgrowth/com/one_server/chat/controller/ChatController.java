package dgrowth.com.one_server.chat.controller;

import dgrowth.com.one_server.chat.dto.request.CreateRoomRequest;
import dgrowth.com.one_server.chat.dto.response.ChatMessageResponse;
import dgrowth.com.one_server.chat.dto.response.ChatRoomResponse;
import dgrowth.com.one_server.chat.dto.response.ChatUserResponse;
import dgrowth.com.one_server.chat.service.ChatService;
import dgrowth.com.one_server.chat.dto.request.SendMessageRequest;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/createRoom")
    public ResponseEntity<ChatRoomResponse> createChatRoom(
        @RequestBody CreateRoomRequest createRoomRequest, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(chatService.create(createRoomRequest, httpServletRequest));
    }

    @PostMapping("/joinRoom/{roomId}")
    public ResponseEntity<ChatRoomResponse> joinChatRoom(@PathVariable("roomId") Long roomId, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(chatService.join(roomId, httpServletRequest));
    }

    @PostMapping("/send/{roomId}")
    public ResponseEntity<ChatMessageResponse> sendMessage(@RequestBody SendMessageRequest sendMessageRequest, HttpServletRequest httpServletRequest, @PathVariable("roomId") Long roomId){
        return ResponseEntity.ok().body(chatService.send(sendMessageRequest, httpServletRequest, roomId));
    }

    @PostMapping("/history/{roomId}")
    public ResponseEntity<List<ChatMessageResponse>> history(@PathVariable("roomId") Long roomId,
        HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(chatService.getHistory(roomId, httpServletRequest));
    }

    @GetMapping("/allUsers/{roomId}")
    public ResponseEntity<List<ChatUserResponse>> getAllChatters(@PathVariable("roomId") Long roomId) {
        return ResponseEntity.ok().body(chatService.getAllUsers(roomId));
    }


}