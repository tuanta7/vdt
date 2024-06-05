package com.vdt.fosho.service;

import com.vdt.fosho.dto.ShippingAddressDTO;
import com.vdt.fosho.entity.ShippingAddress;
import com.vdt.fosho.repository.ShippingAddressRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;

    public ShippingAddress createShippingAddress(ShippingAddress shippingAddress) {
        return shippingAddressRepository.save(shippingAddress);
    }

    public ShippingAddressDTO toDTO(ShippingAddress shippingAddress) {
        return ShippingAddressDTO.builder()
                .id(shippingAddress.getId())
                .name(shippingAddress.getName())
                .address(shippingAddress.getAddress())
                .latitude(shippingAddress.getCoordinates().getY())
                .longitude(shippingAddress.getCoordinates().getX())
                .build();
    }
}
