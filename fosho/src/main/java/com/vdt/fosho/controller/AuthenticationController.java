package com.vdt.fosho.controller;

import com.vdt.fosho.dto.LoginDTO;
import com.vdt.fosho.dto.RegisterDTO;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.service.AuthenticationService;
import com.vdt.fosho.service.LogoutService;
import com.vdt.fosho.service.UserService;
import com.vdt.fosho.utils.JSendResponse;
import com.vdt.fosho.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;
    private final UserService userService;
    private final LogoutService logoutService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public JSendResponse<HashMap<String, Object>> register(
            @RequestBody RegisterDTO registerDTO
    ) {

        User user = authService.register(registerDTO);
        String accessToken = jwtUtil.generateToken(user);
        authService.saveToken(user, accessToken);

        HashMap<String, Object> data = new HashMap<>();
        data.put("user", userService.toUserDTO(user));
        data.put("access_token", accessToken);

        return JSendResponse.success(data);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, Object>> register(
            @RequestBody LoginDTO loginDTO
    ) {

        User user = authService.login(loginDTO);
        String accessToken = jwtUtil.generateToken(user);
        authService.saveToken(user, accessToken);

        HashMap<String, Object> data = new HashMap<>();
        data.put("user", userService.toUserDTO(user));
        data.put("access_token", accessToken);

        return JSendResponse.success(data);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, Object>> logout(
            @RequestHeader("Authorization") String authorization
    ) {
        return JSendResponse.success(null);
    }
}
