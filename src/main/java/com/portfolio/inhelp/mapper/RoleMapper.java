package com.portfolio.inhelp.mapper;

import com.portfolio.inhelp.dto.RoleDto;
import com.portfolio.inhelp.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDto toDto(Role role);
}
