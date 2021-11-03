package dgrowth.com.one_server.data.dto.mapper;

import dgrowth.com.one_server.data.dto.response.MajorResponse;
import dgrowth.com.one_server.domain.entity.Major;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MajorMapper extends  EntityMapper<MajorResponse, Major> {

    MajorMapper INSTANCE = Mappers.getMapper(MajorMapper.class);
}
