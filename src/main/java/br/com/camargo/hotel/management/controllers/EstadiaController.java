package br.com.camargo.hotel.management.controllers;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.estadia.domain.dtos.EstadiaDTO;
import br.com.camargo.hotel.management.estadia.domain.viewobjects.EstadiaVO;
import br.com.camargo.hotel.management.estadia.services.EstadiaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estadia")
@RequiredArgsConstructor
public class EstadiaController {

    private final EstadiaService service;

    @GetMapping
    public ResponseEntity<Page<EstadiaVO>> visualizarEstadias(@RequestParam Paginator paginator) {
        return service.visualizarEstadias(paginator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadiaVO> buscarEstadia(@PathVariable Long id) {
        return service.buscarEstadia(id);
    }

    @PostMapping
    public ResponseEntity<ResponseVO<EstadiaVO>> realizarCheckIn(@RequestBody @Valid EstadiaDTO estadiaDTO) {
        return service.realizarCheckIn(estadiaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseVO<EstadiaVO>> realizarCheckOut(@PathVariable Long id) {
        return service.realizarCheckOut(id);
    }
}
