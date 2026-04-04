package br.com.c137.project.core.mappers;

import br.com.c137.project.core.multitenancy.tenant.dtos.gets.UnitMeasureGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.UnitMeasurePostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.UnitMeasurePutDTO;
import br.com.c137.project.core.multitenancy.tenant.models.UnitMeasure;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UnitMeasureMapper {
    UnitMeasureGetDTO unitMeasureToUnitMeasureGetDTO(UnitMeasure unitMeasure);
    UnitMeasure postToUnitMeasure(UnitMeasurePostDTO unitMeasurePostDTO);
    UnitMeasure putToUnitMeasure(UnitMeasurePutDTO unitMeasurePutDTO,  @MappingTarget UnitMeasure unitMeasure);
}
