package progrohan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progrohan.backend.dto.LoginResponseDto;
import progrohan.backend.dto.UserCreateRequestDto;
import progrohan.backend.dto.UserRequestDto;
import progrohan.backend.dto.UserResponseDto;
import progrohan.backend.entity.UserEntity;
import progrohan.backend.exception.BadCredentialsException;
import progrohan.backend.exception.UserExistException;
import progrohan.backend.mapper.UserMapper;
import progrohan.backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto createUser(UserCreateRequestDto userRequestDTO) {

        if (userRepository.findByUsername(userRequestDTO.name()).isPresent())
            throw new UserExistException("User with username " + userRequestDTO.name() + " already exists!");

        UserEntity entity = userMapper.toEntity(userRequestDTO);

        entity.setPassword(passwordEncoder.encode(userRequestDTO.password()));

        UserEntity userEntity = userRepository.saveAndFlush(entity);


        return userMapper.toDto(userEntity);

    }


    public LoginResponseDto loginUser(UserRequestDto userRequestDTO) {


        UserEntity userEntity = userRepository.findByUsername(userRequestDTO.name())
                .orElseThrow(() -> new BadCredentialsException("Username or password is incorrect!"));

        if(!passwordEncoder.matches(userRequestDTO.password(), userEntity.getPassword())){
            throw new BadCredentialsException("Username or password is incorrect!");
        }

        String token = jwtService.generateToken(userEntity.getId());

        return new LoginResponseDto(token);


    }

}
