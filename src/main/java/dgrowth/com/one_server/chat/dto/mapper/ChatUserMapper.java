package dgrowth.com.one_server.chat.dto.mapper;

import dgrowth.com.one_server.base.mapper.BaseMapper;
import dgrowth.com.one_server.chat.dto.response.ChatUserResponse;
import dgrowth.com.one_server.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatUserMapper extends BaseMapper<ChatUserResponse, User> {

    ChatUserMapper INSTANCE = Mappers.getMapper(ChatUserMapper.class);
}
