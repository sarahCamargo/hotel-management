package br.com.camargo.hotel.management.controllers;

import br.com.camargo.hotel.management.commons.Page;
import br.com.camargo.hotel.management.commons.Paginator;
import br.com.camargo.hotel.management.commons.ResponseVO;
import br.com.camargo.hotel.management.hospede.services.HospedeService;
import br.com.camargo.hotel.management.hospede.structures.dtos.HospedeDTO;
import br.com.camargo.hotel.management.hospede.structures.viewobjects.HospedeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospedes")
@RequiredArgsConstructor
public class HospedeController {

    private final HospedeService service;

    @GetMapping
    public ResponseEntity<Page<HospedeVO>> findAll(Paginator paginator) {
        return service.findAll(paginator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospedeVO> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseVO<HospedeVO>> create(@RequestBody HospedeDTO hospedeDTO) {
        return service.create(hospedeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseVO<HospedeVO>> update(@PathVariable Long id, @RequestBody HospedeDTO hospedeDTO) {
        return service.update(id, hospedeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseVO<HospedeVO>> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
