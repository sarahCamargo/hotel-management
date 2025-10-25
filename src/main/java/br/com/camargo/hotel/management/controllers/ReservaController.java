package br.com.camargo.hotel.management.controllers;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.reserva.services.ReservaService;
import br.com.camargo.hotel.management.reserva.domain.dtos.ReservaDTO;
import br.com.camargo.hotel.management.reserva.domain.viewobjects.ReservaVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService service;

    @GetMapping
    public ResponseEntity<Page<ReservaVO>> visualizarReservas(Paginator paginator) {
        return service.visualizarReservas(paginator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaVO> buscarReserva(@PathVariable Long id) {
        return service.buscarReserva(id);
    }

    @PostMapping
    public ResponseEntity<ResponseVO<ReservaVO>> reservar(@RequestBody @Valid ReservaDTO reservaDTO) {
        return service.reservar(reservaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseVO<ReservaVO>> alterarReserva(@PathVariable Long id, @RequestBody ReservaDTO reservaDTO) {
        return service.alterarReserva(id, reservaDTO);
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<ResponseVO<ReservaVO>> cancelarReserva(@PathVariable Long id) {
        return service.cancelarReserva(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseVO<ReservaVO>> deletarReserva(@PathVariable Long id) {
        return service.deletarReserva(id);
    }
}
