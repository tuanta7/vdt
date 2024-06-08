package com.vdt.fosho.service;

import com.vdt.fosho.dto.ShippingAddressDTO;
import com.vdt.fosho.dto.UserDTO;
import com.vdt.fosho.entity.ShippingAddress;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.exception.ResourceNotFoundException;
import com.vdt.fosho.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ShippingAddressService shippingAddressService;

    public User findByEmail(String userEmail) {
        Optional<User> result = userRepository.findByEmail(userEmail);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("User not found with email: " + userEmail);
        }
        return result.get();
    }

    public UserDTO toDTO(User user) {
        List<ShippingAddressDTO> shippingAddressesDTO = new ArrayList<>();
        if (user.getShippingAddresses() != null) {
            for (ShippingAddress sa: user.getShippingAddresses()) {
                shippingAddressesDTO.add(shippingAddressService.toDTO(sa));
            }
        }
        return  UserDTO.builder()
                        .id(user.getId())
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .avatarUrl(user.getAvatarUrl())
                        .shippingAddressesDTO(shippingAddressesDTO)
                        .build();
    }
}
