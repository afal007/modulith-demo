package com.example.modulith.demo.cargo.management;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.modulith.demo.cargo.management.api.model.TransportationRequestDTO;
import com.example.modulith.demo.cargo.management.api.spring.web.TransportationRequestV1Api;

@RestController
public class TransportationRequestsController implements TransportationRequestV1Api {
    @Override
    public ResponseEntity<TransportationRequestDTO> getV1TransportationRequestsId(Long id) {
        return TransportationRequestV1Api.super.getV1TransportationRequestsId(id);
    }

    @Override
    public ResponseEntity<Void> postV1TransportationRequests(List<TransportationRequestDTO> transportationRequestDTO) {
        return TransportationRequestV1Api.super.postV1TransportationRequests(transportationRequestDTO);
    }
}
