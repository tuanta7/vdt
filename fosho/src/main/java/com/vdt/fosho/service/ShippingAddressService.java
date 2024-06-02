package com.vdt.fosho.service;

import com.vdt.fosho.dto.ShippingAddressDTO;
import com.vdt.fosho.entity.ShippingAddress;
import com.vdt.fosho.repository.ShippingAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingAddressService {

    ShippingAddressRepository shippingAddressRepository;

    public ShippingAddress createShippingAddress(ShippingAddress shippingAddress) {
        return shippingAddressRepository.save(shippingAddress);
    }

    public ShippingAddressDTO toShippingAddressDTO(ShippingAddress shippingAddress) {
        return new ShippingAddressDTO(
                shippingAddress.getId(),
                shippingAddress.getName(),
                shippingAddress.getAddress(),
                shippingAddress.getCoordinates().getX(),
                shippingAddress.getCoordinates().getY()
        );
    }
}
