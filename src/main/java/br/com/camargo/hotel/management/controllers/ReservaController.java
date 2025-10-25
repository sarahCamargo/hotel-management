package br.com.camargo.hotel.management.controllers;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.reserva.services.ReservaService;
import br.com.camargo.hotel.management.reserva.domain.dtos.ReservaDTO;
import br.com.camargo.hotel.management.reserva.domain.viewobjects.ReservaVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Gerenciamento de reservas do hotel")
public class ReservaController {

    private final ReservaService service;

    @GetMapping
    @Operation(
            summary = "Listar reservas",
            description = "Retorna uma lista paginada de todas as reservas registradas no sistema."
    )
    public ResponseEntity<Page<ReservaVO>> visualizarReservas(Paginator paginator) {
        return service.visualizarReservas(paginator);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar reserva por ID",
            description = "Retorna os detalhes de uma reserva específica conforme o ID informado."
    )
    public ResponseEntity<ReservaVO> buscarReserva(@PathVariable Long id) {
        return service.buscarReserva(id);
    }

    @PostMapping
    @Operation(
            summary = "Criar reserva",
            description = "Registra uma nova reserva no sistema com os dados fornecidos no corpo da requisição."
    )
    public ResponseEntity<ResponseVO<ReservaVO>> reservar(@RequestBody @Valid ReservaDTO reservaDTO) {
        return service.reservar(reservaDTO);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Alterar reserva",
            description = "Atualiza os dados de uma reserva existente conforme o ID informado."
    )
    public ResponseEntity<ResponseVO<ReservaVO>> alterarReserva(@PathVariable Long id, @RequestBody ReservaDTO reservaDTO) {
        return service.alterarReserva(id, reservaDTO);
    }

    @PutMapping("/cancelar/{id}")
    @Operation(
            summary = "Cancelar reserva",
            description = "Cancela uma reserva existente identificada pelo ID informado."
    )
    public ResponseEntity<ResponseVO<ReservaVO>> cancelarReserva(@PathVariable Long id) {
        return service.cancelarReserva(id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar reserva",
            description = "Exclui uma reserva do sistema conforme o ID informado."
    )
    public ResponseEntity<ResponseVO<ReservaVO>> deletarReserva(@PathVariable Long id) {
        return service.deletarReserva(id);
    }
}
