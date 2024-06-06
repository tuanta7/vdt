package com.vdt.fosho.elasticsearch.repository;

import com.vdt.fosho.elasticsearch.document.RestaurantDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantDocumentRepository extends ElasticsearchRepository<RestaurantDocument, Long>{

    List<RestaurantDocument> findByLatitudeBetweenAndLongitudeBetween(
            double minLatitude,
            double maxLatitude,
            double minLongitude,
            double maxLongitude
    );
}
