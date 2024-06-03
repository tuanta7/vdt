package com.vdt.fosho.service;

import com.vdt.fosho.dto.RestaurantDTO;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.exception.ResourceNotFoundException;
import com.vdt.fosho.repository.RestaurantRepository;

import com.vdt.fosho.utils.GeoUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RestaurantService {

    private RestaurantRepository restaurantRepository;

    public List<Restaurant> getAllRestaurants() {
        // The implementation of "findAll()" is provided dynamically at runtime by Spring Data JPA.
        return restaurantRepository.findAll();
    }


    public Restaurant getRestaurantById(Long id) {
        Optional<Restaurant> result = restaurantRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + id);
        }
        return result.get();
    }


    public List<Restaurant> getRestaurantsByOwnerId(Long ownerId) {
        return restaurantRepository.findByOwnerId(ownerId);
    }


    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }


    public Restaurant updateRestaurant(Long id, Restaurant restaurant) {
        Optional<Restaurant> result = restaurantRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + id);

        }
        Restaurant existingRestaurant = result.get();
        existingRestaurant.setName(restaurant.getName());
        existingRestaurant.setAddress(restaurant.getAddress());
        existingRestaurant.setPhone(restaurant.getPhone());
        existingRestaurant.setOpenTime(restaurant.getOpenTime());
        existingRestaurant.setCloseTime(restaurant.getCloseTime());
        return restaurantRepository.save(existingRestaurant);
    }


    public void deleteRestaurant(Long id) {
        Optional<Restaurant> result = restaurantRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + id);
        }
        restaurantRepository.deleteById(id);
    }


    public Restaurant updateRestaurantAddress(Long id, String address, double longitude, double latitude) {
        Optional<Restaurant> result = restaurantRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + id);
        }
        Restaurant existingRestaurant = result.get();
        existingRestaurant.setCoordinates(GeoUtils.createPoint(latitude, longitude));
        existingRestaurant.setAddress(address);
        return restaurantRepository.save(existingRestaurant);
    }


    public Restaurant updateRestaurantLogo(Long id, String logoUrl, String logoPublicId) {
        Optional<Restaurant> result = restaurantRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + id);
        }
        Restaurant existingRestaurant = result.get();
        existingRestaurant.setLogoUrl(logoUrl);
        existingRestaurant.setLogoPublicId(logoPublicId);
        return restaurantRepository.save(existingRestaurant);
    }

    public RestaurantDTO toDTO(Restaurant restaurant) {
        return  RestaurantDTO.builder()
                .id(restaurant.getId())
                .address(restaurant.getAddress())
                .name(restaurant.getName())
                .phone(restaurant.getPhone())
                .logoUrl(restaurant.getLogoUrl())
                .rating(restaurant.getRating())
                .isOpen(restaurant.isOpen())
                .openTime(restaurant.getOpenTime())
                .closeTime(restaurant.getCloseTime())
                .latitude(restaurant.getCoordinates().getY())
                .longitude(restaurant.getCoordinates().getX())
                .build();
    }

}
