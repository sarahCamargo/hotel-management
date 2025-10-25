package br.com.camargo.hotel.management.controllers;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.hospede.queries.HospedeFiltros;
import br.com.camargo.hotel.management.hospede.services.HospedeService;
import br.com.camargo.hotel.management.hospede.domain.dtos.HospedeDTO;
import br.com.camargo.hotel.management.hospede.domain.viewobjects.HospedeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospedes")
@RequiredArgsConstructor
@Tag(name = "Hóspedes", description = "Gerenciamento de hóspedes do hotel.")
public class HospedeController {

    private final HospedeService service;

    @GetMapping
    @Operation(
            summary = "Listar hóspedes",
            description = "Retorna uma lista paginada de todos os hóspedes do hotel, podendo receber parâmetros de paginação e filtros."
    )
    public ResponseEntity<Page<HospedeVO>> listarHospedes(@ModelAttribute HospedeFiltros filtros, Paginator paginator) {
        return service.visualizarHospedes(filtros, paginator);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar hóspede por ID",
            description = "Retorna as informações completas de um hóspede existente, conforme o ID informado."
    )
    public ResponseEntity<HospedeVO> buscarHospede(@PathVariable Long id) {
        return service.buscarHospede(id);
    }

    @PostMapping
    @Operation(
            summary = "Cadastrar hóspede",
            description = "Cria um novo registro de hóspede com os dados informados no corpo da requisição."
    )
    public ResponseEntity<ResponseVO<HospedeVO>> cadastrarHospede(@RequestBody @Valid HospedeDTO hospedeDTO) {
        return service.cadastrarHospede(hospedeDTO);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Alterar hóspede",
            description = "Atualiza as informações de um hóspede existente identificado pelo ID."
    )
    public ResponseEntity<ResponseVO<HospedeVO>> alterarHospede(@PathVariable Long id, @RequestBody @Valid HospedeDTO hospedeDTO) {
        return service.alterarHospede(id, hospedeDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remover hóspede",
            description = "Exclui um hóspede do sistema conforme o ID informado."
    )
    public ResponseEntity<ResponseVO<HospedeVO>> removerHospede(@PathVariable Long id) {
        return service.removerHospede(id);
    }
}
