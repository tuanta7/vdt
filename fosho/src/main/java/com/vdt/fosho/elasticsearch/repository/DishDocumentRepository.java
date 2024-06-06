package com.vdt.fosho.elasticsearch.repository;

import com.vdt.fosho.elasticsearch.document.DishDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishDocumentRepository extends ElasticsearchRepository<DishDocument, Long> {
}
