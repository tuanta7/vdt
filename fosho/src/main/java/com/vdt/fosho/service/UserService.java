package com.vdt.fosho.service;

import com.vdt.fosho.dto.UserDTO;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.exception.ResourceNotFoundException;
import com.vdt.fosho.repository.RestaurantRepository;
import com.vdt.fosho.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String userEmail) {
        Optional<User> result = userRepository.findByEmail(userEmail);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("User not found with email: " + userEmail);
        }
        return result.get();
    }

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
