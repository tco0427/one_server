package dgrowth.com.one_server.major.dto.mapper;

import dgrowth.com.one_server.base.mapper.BaseMapper;
import dgrowth.com.one_server.major.dto.request.MajorRequest;
import dgrowth.com.one_server.major.dto.response.MajorResponse;
import dgrowth.com.one_server.major.domain.entity.Major;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MajorMapper extends BaseMapper<MajorResponse, Major> {

    MajorMapper INSTANCE = Mappers.getMapper(MajorMapper.class);

    Major requestToEntity(MajorRequest majorRequest);

    @Override
    @Mapping(source = "notices", target = "notices", ignore = true)
    MajorResponse toDto(Major entity);

    @Override
    @Mapping(source = "notices", target = "notices", ignore = true)
    Major toEntity(MajorResponse dto);
}
