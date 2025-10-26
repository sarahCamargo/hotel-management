package br.com.camargo.hotel.management.controllers;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.estadia.domain.dtos.EstadiaDTO;
import br.com.camargo.hotel.management.estadia.domain.viewobjects.EstadiaVO;
import br.com.camargo.hotel.management.estadia.services.EstadiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estadias")
@RequiredArgsConstructor
@Tag(name = "Estadias", description = "Gerenciamento de check-in/check-out")
public class EstadiaController {

    private final EstadiaService service;

    @GetMapping
    @Operation(
            summary = "Listar estadias",
            description = "Retorna uma lista paginada de todas as estadias do hotel, podendo receber parâmetros de paginação."
    )
    public ResponseEntity<Page<EstadiaVO>> visualizarEstadias(Paginator paginator) {
        return service.visualizarEstadias(paginator);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar estadia por ID",
            description = "Retorna os detalhes de uma estadia específica, identificada pelo ID informado."
    )
    public ResponseEntity<EstadiaVO> buscarEstadia(@PathVariable Long id) {
        return service.buscarEstadia(id);
    }

    @PostMapping
    @Operation(
            summary = "Realizar check-in",
            description = "Registra uma nova estadia e realiza o check-in do hóspede conforme os dados fornecidos no corpo da requisição."
    )
    public ResponseEntity<ResponseVO<EstadiaVO>> realizarCheckIn(@RequestBody @Valid EstadiaDTO estadiaDTO) {
        return service.realizarCheckIn(estadiaDTO);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Realizar check-out",
            description = "Finaliza a estadia de um hóspede e realiza o check-out conforme o ID da estadia."
    )
    public ResponseEntity<ResponseVO<EstadiaVO>> realizarCheckOut(@PathVariable Long id) {
        return service.realizarCheckOut(id);
    }
}
