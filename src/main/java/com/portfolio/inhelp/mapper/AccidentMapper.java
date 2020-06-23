package com.portfolio.inhelp.mapper;

import com.portfolio.inhelp.dto.AccidentDto;
import com.portfolio.inhelp.model.Accident;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccidentMapper {

    AccidentMapper INSTANCE = Mappers.getMapper(AccidentMapper.class);

    @Mapping(target = "userId", source = "user.id")
    AccidentDto toDto(Accident accident);
}
