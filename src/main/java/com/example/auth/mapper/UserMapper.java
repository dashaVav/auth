package com.example.auth.mapper;

import com.example.auth.dto.UserAuthDTO;
import com.example.auth.model.User;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface UserMapper extends Converter<UserAuthDTO, User> {
    User convert(@NotNull UserAuthDTO userAuthDTO);
}
