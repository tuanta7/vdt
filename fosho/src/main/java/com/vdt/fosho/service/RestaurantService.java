package com.vdt.fosho.service;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.vdt.fosho.dto.RestaurantDTO;
import com.vdt.fosho.elasticsearch.document.RestaurantDocument;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.exception.ResourceNotFoundException;
import com.vdt.fosho.elasticsearch.repository.RestaurantDocumentRepository;
import com.vdt.fosho.repository.RestaurantRepository;

import com.vdt.fosho.utils.GeoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

    public Page<RestaurantDocument> getAllRestaurants(String search, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        if (search.isEmpty()) {
            System.out.println("Searching all dishes in Elasticsearch");
            return restaurantDocumentRepository.findAll(pageable);
        }
        return restaurantDocumentRepository.findByName(search, pageable);  // Elasticsearch
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


    public Restaurant createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = toEntity(restaurantDTO);
        return restaurantRepository.save(restaurant);
    }


    public Restaurant updateRestaurant(Long id, RestaurantDTO restaurantDTO) {
        Optional<Restaurant> result = restaurantRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + id);

        }
        Restaurant existingRestaurant = result.get();
        existingRestaurant.setName(restaurantDTO.getName());
        existingRestaurant.setAddress(restaurantDTO.getAddress());
        existingRestaurant.setPhone(restaurantDTO.getPhone());
        existingRestaurant.setOpenTime(restaurantDTO.getOpenTime());
        existingRestaurant.setCloseTime(restaurantDTO.getCloseTime());
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
                .ownerId(restaurant.getOwner().getId())
                .build();
    }

    // Accepted input fields: name, address, phone, latitude, longitude, openTime, closeTime
    public Restaurant toEntity(RestaurantDTO restaurantDTO) {
        return Restaurant.builder()
                .name(restaurantDTO.getName())
                .address(restaurantDTO.getAddress())
                .phone(restaurantDTO.getPhone())
                .openTime(restaurantDTO.getOpenTime())
                .closeTime(restaurantDTO.getCloseTime())
                .coordinates(GeoUtils.createPoint(restaurantDTO.getLatitude(), restaurantDTO.getLongitude()))
                .isActive(true)
                .owner(restaurantDTO.getOwner())
                .build();
    }

    public Long getRestaurantOwnerIdById(Long restaurantId) {
        return restaurantRepository.getOwnerIdById(restaurantId);
    }
}
