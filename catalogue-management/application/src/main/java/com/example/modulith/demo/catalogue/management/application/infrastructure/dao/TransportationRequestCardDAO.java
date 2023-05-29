package com.example.modulith.demo.catalogue.management.application.infrastructure.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.modulith.demo.catalogue.management.application.domain.TransportationRequestCard;

public interface TransportationRequestCardDAO extends ElasticsearchRepository<TransportationRequestCard, Long> {

    @Query(
        value = """
                {
                    "query_string": {
                      "query": "?0*"
                    }
                }
                """
    )
    Page<TransportationRequestCard> findByQuery(String q, Pageable pageable);
}
