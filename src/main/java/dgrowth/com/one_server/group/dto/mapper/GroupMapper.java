package dgrowth.com.one_server.group.dto.mapper;

import dgrowth.com.one_server.base.mapper.BaseMapper;
import dgrowth.com.one_server.group.dto.request.GroupRequest;
import dgrowth.com.one_server.group.dto.response.GroupResponse;
import dgrowth.com.one_server.group.domain.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupMapper extends BaseMapper<GroupResponse, Group> {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    Group requestToEntity(GroupRequest groupRequest, Long hostId);

    @Override
    @Mapping(source = "notices", target = "notices", ignore = true)
    @Mapping(target = "isParticipant", ignore = true)
    @Mapping(target = "numParticipants", ignore = true)
    GroupResponse toDto(Group entity);

    @Override
    @Mapping(source = "notices", target = "notices", ignore = true)
    Group toEntity(GroupResponse dto);
}
