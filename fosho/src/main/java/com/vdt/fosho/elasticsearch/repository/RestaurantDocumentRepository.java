package com.vdt.fosho.elasticsearch.repository;

import com.vdt.fosho.elasticsearch.document.RestaurantDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
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

    @Query("{\"bool\": {\"must\": [{\"wildcard\": {\"name\": {\"value\": \"*?0*\", \"case_insensitive\": true}}}]}}")
    Page<RestaurantDocument> findByName(@Param("search") String search, Pageable pageable);
}
