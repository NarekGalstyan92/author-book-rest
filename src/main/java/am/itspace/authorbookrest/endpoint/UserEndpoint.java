package am.itspace.authorbookrest.endpoint;

import am.itspace.authorbookrest.dto.AuthRequestDto;
import am.itspace.authorbookrest.dto.AuthResponseDto;
import am.itspace.authorbookrest.dto.SaveUserDto;
import am.itspace.authorbookrest.dto.UserResponseDto;
import am.itspace.authorbookrest.entity.User;
import am.itspace.authorbookrest.mapper.UserMapper;
import am.itspace.authorbookrest.service.UserService;
import am.itspace.authorbookrest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserEndpoint {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${upload.image.path}")
    private String uploadImagePath;

    @PostMapping
    public ResponseEntity<UserResponseDto> register(@RequestBody SaveUserDto saveUserDto) {
        User byEmail = userService.findByEmail(saveUserDto.getEmail());
        if (byEmail != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(userService.create(saveUserDto));
    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        File file = new File(uploadImagePath, picName);
        if (file.exists()) {
            return IOUtils.toByteArray(new FileInputStream(file));
        }
        return null;
    }

    @PostMapping("/image/{id}")
    public ResponseEntity<UserResponseDto> uploadImage(@PathVariable("id") int userId, @RequestParam(value = "picture", required = false) MultipartFile multipartFile) throws IOException {
        User userById = userService.findById(userId);
        if (userById == null) {
            return ResponseEntity.notFound().build();
        }

        userService.uploadImage(userById, multipartFile);

        return ResponseEntity.ok(userMapper.map(userById));
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        User userByEmail = userService.findByEmail(authRequestDto.getEmail());
        if (userByEmail == null || !passwordEncoder.matches(authRequestDto.getPassword(), userByEmail.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(AuthResponseDto.builder()
                .token(jwtTokenUtil.generateToken(userByEmail.getEmail()))
                .userResponseDto(userMapper.map(userByEmail))
                .build());
    }
}
