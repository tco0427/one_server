package dgrowth.com.one_server.chat.dto.mapper;

import dgrowth.com.one_server.base.mapper.BaseMapper;
import dgrowth.com.one_server.chat.dto.request.SendMessageRequest;
import dgrowth.com.one_server.chat.dto.response.ChatMessageResponse;
import dgrowth.com.one_server.chat.domain.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessageResponse, ChatMessage> {

    ChatMessageMapper INSTANCE = Mappers.getMapper(ChatMessageMapper.class);

    @Mapping(target = "senderId", ignore = true)
    @Mapping(target = "sentDateTime", ignore = true)
    @Mapping(target = "chatRoomId", ignore = true)
    ChatMessage requestToEntity(SendMessageRequest sendMessageRequest);
}
