package com.vdt.fosho.elasticsearch.repository;

import com.vdt.fosho.elasticsearch.document.DishDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishDocumentRepository extends ElasticsearchRepository<DishDocument, Long> {

   Page<DishDocument> findByName(String name, Pageable pageable);
}
