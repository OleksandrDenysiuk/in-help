package com.portfolio.inhelp.mapper;

import com.portfolio.inhelp.dto.*;
import com.portfolio.inhelp.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "avatarId", source = "avatar.id")
    UserDto toDto(User user);

    @Mapping(target = "userId", source = "user.id")
    AccidentDto toDto(Accident accident);

    RoleDto toDto(Role role);

    @Mapping(target = "accidentId", source = "accident.id")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "newsId", source = "news.id")
    CommentDto toDto(Comment comment);

    @Mapping(target = "accidentId", source = "accident.id")
    NewsDto toDto(News news);

    @Mapping(target = "accidentId", source = "accident.id")
    @Mapping(target = "newsId", source = "news.id")
    ImageDto toDto(Image image);
}
