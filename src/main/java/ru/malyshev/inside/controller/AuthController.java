package ru.malyshev.inside.controller;

import ru.malyshev.inside.dto.AuthenticationRequestDto;
import ru.malyshev.inside.dto.TokenDto;
import ru.malyshev.inside.model.User;
import ru.malyshev.inside.security.jwt.JwtTokenProvider;
import ru.malyshev.inside.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping(value = "authenticate")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            TokenDto tokenDto = new TokenDto(jwtTokenProvider.createToken(username, user.getRoles()));
            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "addNewUser")
    public ResponseEntity<?> addNewUser(@RequestBody User user) {
        try {
            userService.register(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка создания нового пользователя", HttpStatus.BAD_REQUEST);
        }
    }
}