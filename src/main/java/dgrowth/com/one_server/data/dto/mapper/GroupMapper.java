package dgrowth.com.one_server.data.dto.mapper;

import dgrowth.com.one_server.data.dto.request.GroupRequest;
import dgrowth.com.one_server.data.dto.response.GroupResponse;
import dgrowth.com.one_server.domain.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupMapper extends EntityMapper<GroupResponse, Group> {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    Group requestToEntity(GroupRequest groupRequest, Long hostId);
}
