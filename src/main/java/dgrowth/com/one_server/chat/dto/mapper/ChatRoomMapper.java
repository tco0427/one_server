package dgrowth.com.one_server.chat.dto.mapper;

import dgrowth.com.one_server.base.mapper.BaseMapper;
import dgrowth.com.one_server.chat.domain.entity.ChatRoom;
import dgrowth.com.one_server.chat.dto.request.CreateRoomRequest;
import dgrowth.com.one_server.chat.dto.response.ChatRoomResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatRoomMapper extends BaseMapper<ChatRoomResponse, ChatRoom> {

    ChatRoomMapper INSTANCE = Mappers.getMapper(ChatRoomMapper.class);

    @Mapping(source = "participantsId", target = "userIdList", ignore = true)
    ChatRoom requestToEntity(CreateRoomRequest createRoomRequest);
}
