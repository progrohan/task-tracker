package progrohan.backend.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progrohan.backend.dto.UserResponseDto;
import progrohan.backend.entity.UserEntity;
import progrohan.backend.service.UserService;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUser(@AuthenticationPrincipal UserDetails userDetails){

        UserEntity user = userService.loadUserEntityByUsername(userDetails.getUsername());

        UserResponseDto userResponseDTO = new UserResponseDto(user.getUsername());

        return ResponseEntity.ok(userResponseDTO);
    }

}
