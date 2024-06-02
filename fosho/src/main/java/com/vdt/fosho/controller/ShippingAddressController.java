package com.vdt.fosho.controller;

import com.vdt.fosho.dto.RestaurantDTO;
import com.vdt.fosho.dto.ShippingAddressDTO;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.utils.JSendResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/addresses")
public class ShippingAddressController {

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public JSendResponse<HashMap<String, ShippingAddressDTO>> createRestaurant(
            @Valid @RequestBody RestaurantDTO restaurantDTO
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        restaurantDTO.setOwner((User) authentication.getPrincipal());


        HashMap<String, ShippingAddressDTO> data = new HashMap<>();

        return JSendResponse.success(data);
    }
}
