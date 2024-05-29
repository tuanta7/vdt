package com.vdt.fosho.service;

import com.vdt.fosho.dto.UserDTO;
import com.vdt.fosho.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserDTO toUserDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getAvatarUrl(),
            user.getShippingAddresses()
        );
    }
}
