package dgrowth.com.one_server.chat.service;

import dgrowth.com.one_server.auth.service.AuthService;
import dgrowth.com.one_server.chat.domain.entity.ChatRoom;
import dgrowth.com.one_server.chat.domain.exception.InvalidChatRoomIdException;
import dgrowth.com.one_server.chat.domain.exception.InvalidChatRoomUserException;
import dgrowth.com.one_server.chat.domain.repository.ChatMessageRepository;
import dgrowth.com.one_server.chat.domain.repository.ChatRoomRepository;
import dgrowth.com.one_server.chat.dto.mapper.ChatMessageMapper;
import dgrowth.com.one_server.chat.dto.mapper.ChatRoomMapper;
import dgrowth.com.one_server.chat.dto.mapper.ChatUserMapper;
import dgrowth.com.one_server.chat.dto.request.CreateRoomRequest;
import dgrowth.com.one_server.chat.dto.request.SendMessageRequest;
import dgrowth.com.one_server.chat.dto.response.ChatMessageResponse;
import dgrowth.com.one_server.chat.dto.response.ChatRoomResponse;
import dgrowth.com.one_server.chat.domain.entity.ChatMessage;
import dgrowth.com.one_server.chat.dto.response.ChatUserResponse;
import dgrowth.com.one_server.user.domain.entity.User;
import dgrowth.com.one_server.user.domain.repository.UserRepository;
import dgrowth.com.one_server.user.dto.response.UserResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final AuthService authService;

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final UserRepository userRepository;

    @Transactional
    public ChatRoomResponse create(CreateRoomRequest createRoomRequest, HttpServletRequest httpServletRequest) {

        ChatRoomResponse chatRoomResponse = null;

        String token = authService.getTokenByHeader(httpServletRequest);

        UserResponse userInfo = authService.getUserInfoByToken(token);

        Long userId = userInfo.getId();

        ChatRoom chatRoom = ChatRoomMapper.INSTANCE.requestToEntity(createRoomRequest);

        Set<Long> userIdList = new HashSet<>();
        chatRoom.setUserIdList(userIdList);

        for (Long id : createRoomRequest.getParticipantsId()) {
            userIdList.add(id);
        }

        userIdList.add(userId);

        ChatRoom saved = chatRoomRepository.save(chatRoom);

        chatRoomResponse = ChatRoomMapper.INSTANCE.toDto(saved);

        return chatRoomResponse;
    }

    @Transactional
    public ChatRoomResponse join(Long roomId, HttpServletRequest httpServletRequest) {

        ChatRoomResponse chatRoomResponse = null;

        String token = authService.getTokenByHeader(httpServletRequest);

        UserResponse userInfo = authService.getUserInfoByToken(token);

        Long userId = userInfo.getId();

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
            .orElseThrow(InvalidChatRoomIdException::new);

        Set<Long> userIdList = chatRoom.getUserIdList();

        userIdList.add(userId);

        ChatRoom saved = chatRoomRepository.save(chatRoom);

        chatRoomResponse = ChatRoomMapper.INSTANCE.toDto(saved);

        return chatRoomResponse;
    }

    @Transactional
    public ChatMessageResponse send(SendMessageRequest sendMessageRequest,
        HttpServletRequest httpServletRequest, Long roomId) {

        ChatMessageResponse chatMessageResponse = null;

        String token = authService.getTokenByHeader(httpServletRequest);

        UserResponse userInfo = authService.getUserInfoByToken(token);

        Long senderId = userInfo.getId();

        isInChatRoom(senderId, roomId);

        ChatMessage chatMessage = ChatMessageMapper.INSTANCE.requestToEntity(
            sendMessageRequest);

        chatMessage.setSentDateTime(LocalDateTime.now());
        chatMessage.setSenderId(senderId);
        chatMessage.setChatRoomId(roomId);

        System.out.println("chatMessage = " + chatMessage);

        ChatMessage saved = chatMessageRepository.save(chatMessage);

        chatMessageResponse = ChatMessageMapper.INSTANCE.toDto(saved);

        return chatMessageResponse;
    }

    public List<ChatUserResponse> getAllUsers(Long roomId) {

        List<ChatUserResponse> chatUserResponses = null;

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
            InvalidChatRoomIdException::new);

        Set<Long> userIdList = chatRoom.getUserIdList();

        List<User> users = userRepository.findAllById(userIdList);

        chatUserResponses = ChatUserMapper.INSTANCE.toDto(users);

        return chatUserResponses;
    }

    @Cacheable(value = "id")
    public List<ChatMessageResponse> getHistory(Long roomId,
        HttpServletRequest httpServletRequest) {

        List<ChatMessageResponse> chatMessageResponses = null;

        String token = authService.getTokenByHeader(httpServletRequest);

        UserResponse userInfo = authService.getUserInfoByToken(token);

        isInChatRoom(userInfo.getId(), roomId);

        List<ChatMessage> messages = chatMessageRepository.getChatMessagesByChatRoomId(
            roomId);

        chatMessageResponses = ChatMessageMapper.INSTANCE.toDto(messages);

        return chatMessageResponses;
    }

    private void isInChatRoom(Long userId, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(
            InvalidChatRoomIdException::new);

        if (!chatRoom.getUserIdList().contains(userId)) {
            throw new InvalidChatRoomUserException();
        }
    }
}
