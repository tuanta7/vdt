package com.vdt.fosho.service;

import com.vdt.fosho.dto.ShippingAddressDTO;
import com.vdt.fosho.dto.UserDTO;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.exception.ResourceNotFoundException;
import com.vdt.fosho.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<ShippingAddressDTO> shippingAddressesDTO = null;
        if (user.getShippingAddresses() != null) {
            shippingAddressesDTO = user.getShippingAddresses().stream()
                    .map(shippingAddress -> new ShippingAddressDTO(
                            shippingAddress.getId(),
                            shippingAddress.getName(),
                            shippingAddress.getAddress(),
                            shippingAddress.getCoordinates().getX(),
                            shippingAddress.getCoordinates().getY()
                    ))
                    .collect(Collectors.toList());
        }

        return new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getAvatarUrl(),
                shippingAddressesDTO
        );
    }
}
