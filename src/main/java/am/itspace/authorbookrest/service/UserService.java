package am.itspace.authorbookrest.service;

import am.itspace.authorbookrest.dto.SaveUserDto;
import am.itspace.authorbookrest.dto.UserResponseDto;
import am.itspace.authorbookrest.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    UserResponseDto create(SaveUserDto saveUserDto);

    User findByEmail (String email);

    User findById(int id);

    void uploadImage(User userById, MultipartFile multipartFile) throws IOException;
}
