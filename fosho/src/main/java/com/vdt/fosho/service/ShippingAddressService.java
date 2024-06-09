package com.vdt.fosho.service;

import com.vdt.fosho.dto.ShippingAddressDTO;
import com.vdt.fosho.entity.ShippingAddress;
import com.vdt.fosho.exception.BadRequestException;
import com.vdt.fosho.repository.ShippingAddressRepository;
import com.vdt.fosho.utils.GeoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;

    public List<ShippingAddress> getShippingAddressesByUserId(Long id) {
        return shippingAddressRepository.findByUserIdAndDeletedAtIsNull(id);
    }

    public ShippingAddress createShippingAddress(ShippingAddressDTO shippingAddressDTO) {
        ShippingAddress shippingAddress = toEntity(shippingAddressDTO);
        return shippingAddressRepository.save(shippingAddress);
    }

    public ShippingAddressDTO toDTO(ShippingAddress shippingAddress) {
        return ShippingAddressDTO.builder()
                .id(shippingAddress.getId())
                .name(shippingAddress.getName())
                .address(shippingAddress.getAddress())
                .phone(shippingAddress.getPhone())
                .receiverName(shippingAddress.getReceiverName())
                .latitude(shippingAddress.getCoordinates().getY())
                .longitude(shippingAddress.getCoordinates().getX())
                .build();
    }

    public ShippingAddress toEntity(ShippingAddressDTO shippingAddressDTO) {
        return ShippingAddress.builder()
                .id(shippingAddressDTO.getId())
                .phone(shippingAddressDTO.getPhone())
                .receiverName(shippingAddressDTO.getReceiverName())
                .name(shippingAddressDTO.getName())
                .address(shippingAddressDTO.getAddress())
                .coordinates(GeoUtils.createPoint(shippingAddressDTO.getLatitude(), shippingAddressDTO.getLongitude()))
                .user(shippingAddressDTO.getUser())
                .build();
    }

    public ShippingAddress getShippingAddressesByIdAndUserId(Long id, Long userId) {
        return shippingAddressRepository.findByIdAndUserIdAndDeletedAtIsNull(id, userId)
                .orElseThrow(() -> new BadRequestException("Shipping address not found"));
    }
}
