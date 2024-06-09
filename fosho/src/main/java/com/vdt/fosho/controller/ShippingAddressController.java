package com.vdt.fosho.controller;

import com.vdt.fosho.dto.ShippingAddressDTO;
import com.vdt.fosho.entity.ShippingAddress;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.service.ShippingAddressService;
import com.vdt.fosho.utils.JSendResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@AllArgsConstructor
public class ShippingAddressController {

    private final ShippingAddressService shippingAddressService;

    @PostMapping("/addresses")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public JSendResponse<HashMap<String, ShippingAddressDTO>> addShippingAddress(
            @Valid @RequestBody ShippingAddressDTO shippingAddressDTO
    ) {
        // Get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        shippingAddressDTO.setUser(user); // Set user to create shipping address
        ShippingAddress shippingAddress = shippingAddressService.createShippingAddress(shippingAddressDTO);

        // Build response
        HashMap<String, ShippingAddressDTO> data = new HashMap<>();
        data.put("shipping_address", shippingAddressService.toDTO(shippingAddress));
        return JSendResponse.success(data);
    }
}
