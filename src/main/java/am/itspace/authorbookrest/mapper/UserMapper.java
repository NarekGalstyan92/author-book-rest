package am.itspace.authorbookrest.mapper;

import am.itspace.authorbookrest.dto.SaveUserDto;
import am.itspace.authorbookrest.dto.UserResponseDto;
import am.itspace.authorbookrest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto map(User user);

    @Mapping(target = "userType", constant = "USER")
    User map(SaveUserDto saveUserDto);
}
