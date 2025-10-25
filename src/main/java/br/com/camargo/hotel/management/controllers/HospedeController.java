package br.com.camargo.hotel.management.controllers;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.hospede.services.HospedeService;
import br.com.camargo.hotel.management.hospede.domain.dtos.HospedeDTO;
import br.com.camargo.hotel.management.hospede.domain.viewobjects.HospedeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospedes")
@RequiredArgsConstructor
public class HospedeController {

    private final HospedeService service;

    @GetMapping
    public ResponseEntity<Page<HospedeVO>> listarHospedes(Paginator paginator) {
        return service.listarHospedes(paginator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospedeVO> buscarHospede(@PathVariable Long id) {
        return service.buscarHospede(id);
    }

    @PostMapping
    public ResponseEntity<ResponseVO<HospedeVO>> cadastrarHospede(@RequestBody HospedeDTO hospedeDTO) {
        return service.cadastrarHospede(hospedeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseVO<HospedeVO>> alterarHospede(@PathVariable Long id, @RequestBody HospedeDTO hospedeDTO) {
        return service.alterarHospede(id, hospedeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseVO<HospedeVO>> removerHospede(@PathVariable Long id) {
        return service.removerHospede(id);
    }
}
