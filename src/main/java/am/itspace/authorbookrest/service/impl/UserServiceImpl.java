package am.itspace.authorbookrest.service.impl;

import am.itspace.authorbookrest.dto.SaveUserDto;
import am.itspace.authorbookrest.dto.UserResponseDto;
import am.itspace.authorbookrest.entity.User;
import am.itspace.authorbookrest.mapper.UserMapper;
import am.itspace.authorbookrest.repository.UserRepository;
import am.itspace.authorbookrest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * The AuthorServiceImpl class implements the AuthorService interface and provides
 * the implementation for all its methods.
 *
 * @version 1.0
 * @since 2024-03-10
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${upload.image.path}")
    private String uploadImagePath;

    @Override
    public UserResponseDto create(SaveUserDto saveUserDto) {
        User user = userMapper.map(saveUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.map(userRepository.save(user));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void uploadImage(User user, MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(uploadImagePath, fileName));
            user.setImagePath(fileName);
            userRepository.save(user);
        }
    }
}
