package com.example.Student.Interface;

import com.example.Student.DTO.FiliereDto;
import com.example.Student.shared.GlobalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "filiereClient", url = "http://localhost:8086/api/v1/Filieres")
public interface Filiere {

    @GetMapping("/{id}")
    ResponseEntity<GlobalResponse<FiliereDto>> Getfiliere(@PathVariable("id") Long id);
}
