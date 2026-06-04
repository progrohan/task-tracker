package progrohan.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progrohan.backend.dto.LoginResponseDto;
import progrohan.backend.dto.UserCreateRequestDto;
import progrohan.backend.dto.UserRequestDto;
import progrohan.backend.dto.UserResponseDto;
import progrohan.backend.service.AuthService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateRequestDto userRequestDto) throws URISyntaxException {


        UserResponseDto user = authService.createUser(userRequestDto);

        return ResponseEntity
                .created(new URI("/api/user/me"))
                .body(user);

    }

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponseDto> signIn(@RequestBody UserRequestDto userRequestDto){

        return ResponseEntity.ok(authService.loginUser(userRequestDto));

    }




}
