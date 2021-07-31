package com.hexad.librarymanagement.user.mapper;

import com.hexad.librarymanagement.user.controller.response.UserResponse;
import com.hexad.librarymanagement.user.domain.User;
import com.hexad.librarymanagement.user.service.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    List<UserResponse> mapUserResponseListFrom(List<UserDto> userDtoList);

    List<UserDto> mapUserDtoListFrom(List<User> userList);
}
