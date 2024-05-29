package com.vdt.fosho.controller;

import com.vdt.fosho.dto.LoginDTO;
import com.vdt.fosho.dto.RegisterDTO;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.service.AuthenticationService;
import com.vdt.fosho.service.LogoutService;
import com.vdt.fosho.service.UserService;
import com.vdt.fosho.utils.JSendResponse;
import com.vdt.fosho.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
            HttpServletResponse response,
            @RequestBody RegisterDTO registerDTO
    ) {
        User user = authService.register(registerDTO);
        String accessToken = jwtUtil.generateAccessToken(user);
        authService.saveToken(user, accessToken);

        HashMap<String, Object> data = new HashMap<>();
        data.put("user", userService.toUserDTO(user));
        data.put("access_token", accessToken);

        String refreshToken = jwtUtil.generateRefreshToken(user);
        setRefreshCookie(response, refreshToken);
        return JSendResponse.success(data);
    }



    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, Object>> login(
            HttpServletResponse response,
            @RequestBody LoginDTO loginDTO
    ) {

        User user = authService.login(loginDTO);
        String accessToken = jwtUtil.generateAccessToken(user);
        authService.saveToken(user, accessToken);

        HashMap<String, Object> data = new HashMap<>();
        data.put("user", userService.toUserDTO(user));
        data.put("access_token", accessToken);

        String refreshToken = jwtUtil.generateRefreshToken(user);
        setRefreshCookie(response, refreshToken);
        return JSendResponse.success(data);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, Object>> logout() {
        return JSendResponse.success(null);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, Object>> refresh() {
        return JSendResponse.success(null);
    }

    private static void setRefreshCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(604900000);
        response.addCookie(cookie);
    }
}
