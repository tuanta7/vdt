package com.vdt.fosho.service;

import com.vdt.fosho.dto.RestaurantDTO;
import com.vdt.fosho.elasticsearch.document.RestaurantDocument;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.exception.ResourceNotFoundException;
import com.vdt.fosho.elasticsearch.repository.RestaurantDocumentRepository;
import com.vdt.fosho.repository.RestaurantRepository;

import com.vdt.fosho.utils.GeoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantDocumentRepository restaurantDocumentRepository;

    // Elasticsearch
    public boolean replicateData(String op, RestaurantDocument restaurantDocument) {
        if(op.equals("c") || op.equals("u")) {
            System.out.println("Replicating data to Elasticsearch....");
            restaurantDocumentRepository.save(restaurantDocument);
            return true;
        } else if(op.equals("d")) {
            restaurantDocumentRepository.deleteById(restaurantDocument.getId());
        }
        return true;
    }

    public List<RestaurantDocument> getAllRestaurants() {
        Iterable<RestaurantDocument> restaurantDocuments = restaurantDocumentRepository.findAll();
        return StreamSupport
                .stream(restaurantDocuments.spliterator(), false)
                .collect(Collectors.toList());
    }


    // MariaDB
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
                .isActive(restaurant.isActive())
                .openTime(restaurant.getOpenTime())
                .closeTime(restaurant.getCloseTime())
                .latitude(restaurant.getCoordinates().getY())
                .longitude(restaurant.getCoordinates().getX())
                .build();
    }

}
