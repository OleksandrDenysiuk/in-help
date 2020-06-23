package com.portfolio.inhelp.mapper;

import com.portfolio.inhelp.dto.AccidentDto;
import com.portfolio.inhelp.dto.UserDto;
import com.portfolio.inhelp.model.Accident;
import com.portfolio.inhelp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    @Mapping(target = "userId", source = "user.id")
    AccidentDto toDto(Accident accident);
}
