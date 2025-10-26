package br.com.camargo.hotel.management.hospede.services;

import br.com.camargo.hotel.management.commons.exceptions.MissingEntityException;
import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.hospede.domain.dtos.HospedeDTO;
import br.com.camargo.hotel.management.hospede.domain.entities.Hospede;
import br.com.camargo.hotel.management.hospede.domain.viewobjects.HospedeVO;
import br.com.camargo.hotel.management.hospede.factories.HospedeFactory;
import br.com.camargo.hotel.management.hospede.queries.filter.HospedeFiltros;
import br.com.camargo.hotel.management.hospede.queries.repository.IHospedeQuery;
import br.com.camargo.hotel.management.hospede.repositories.IHospedeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HospedeServiceTest {

    @Mock
    private IHospedeQuery query;

    @Mock
    private IHospedeRepository repository;

    @Mock
    private HospedeFactory factory;

    @InjectMocks
    private HospedeService hospedeService;

    @Test
    void visualizarHospedes_WithResults_ShouldReturnOk() {
        HospedeFiltros filtros = new HospedeFiltros();
        Paginator paginator = Paginator.builder().build();
        Page<HospedeVO> page = Page.<HospedeVO>builder()
                .content(Arrays.asList(new HospedeVO(), new HospedeVO()))
                .build();

        when(query.findAll(filtros, paginator)).thenReturn(page);

        ResponseEntity<Page<HospedeVO>> response = hospedeService.visualizarHospedes(filtros, paginator);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).getContent().size());
    }

    @Test
    void visualizarHospedes_NoContent_ShouldReturnNoContent() {
        HospedeFiltros filtros = new HospedeFiltros();
        Paginator paginator = Paginator.builder().build();
        Page<HospedeVO> page = Page.<HospedeVO>builder()
                .content(Collections.emptyList())
                .build();

        when(query.findAll(filtros, paginator)).thenReturn(page);

        ResponseEntity<Page<HospedeVO>> response = hospedeService.visualizarHospedes(filtros, paginator);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void buscarHospede_ExistingId_ShouldReturnHospede() {
        Long id = 1L;
        HospedeVO hospedeVO = new HospedeVO();

        when(query.findById(id)).thenReturn(hospedeVO);

        ResponseEntity<HospedeVO> response = hospedeService.buscarHospede(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hospedeVO, response.getBody());
    }

    @Test
    void cadastrarHospede_ValidDTO_ShouldCreateSuccessfully() {
        HospedeDTO dto = HospedeDTO.builder()
                .nome("João Silva")
                .cpf("12345678900")
                .telefone("11999999999")
                .build();
        Hospede hospede = new Hospede();
        Hospede savedHospede = new Hospede();
        HospedeVO hospedeVO = new HospedeVO();
        ResponseVO<HospedeVO> responseVO = ResponseVO.<HospedeVO>builder()
                .message("Hóspede criado com sucesso.")
                .data(hospedeVO)
                .build();

        when(factory.fromDTO(dto)).thenReturn(hospede);
        when(repository.save(hospede)).thenReturn(savedHospede);
        when(factory.responseMessage(savedHospede, "Hóspede criado com sucesso."))
                .thenReturn(responseVO);

        ResponseEntity<ResponseVO<HospedeVO>> response = hospedeService.cadastrarHospede(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Hóspede criado com sucesso.", Objects.requireNonNull(response.getBody()).getMessage());
        verify(repository).save(hospede);
    }

    @Test
    void alterarHospede_ExistingId_ShouldUpdateSuccessfully() {
        Long id = 1L;
        HospedeDTO dto = HospedeDTO.builder()
                .nome("João Silva Atualizado")
                .cpf("12345678900")
                .telefone("11999999999")
                .build();
        Hospede hospede = new Hospede();
        Hospede savedHospede = new Hospede();
        HospedeVO hospedeVO = new HospedeVO();
        ResponseVO<HospedeVO> responseVO = ResponseVO.<HospedeVO>builder()
                .message("Hóspede alterado com sucesso.")
                .data(hospedeVO)
                .build();

        when(repository.existsById(id)).thenReturn(true);
        when(factory.fromDTO(id, dto)).thenReturn(hospede);
        when(repository.save(hospede)).thenReturn(savedHospede);
        when(factory.responseMessage(savedHospede, "Hóspede alterado com sucesso."))
                .thenReturn(responseVO);

        ResponseEntity<ResponseVO<HospedeVO>> response = hospedeService.alterarHospede(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hóspede alterado com sucesso.", Objects.requireNonNull(response.getBody()).getMessage());
        verify(repository).save(hospede);
    }

    @Test
    void alterarHospede_NonExistingId_ShouldReturnNotFound() {
        Long id = 999L;
        HospedeDTO dto = new HospedeDTO();

        when(repository.existsById(id)).thenReturn(false);

        ResponseEntity<ResponseVO<HospedeVO>> response = hospedeService.alterarHospede(id, dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(repository, never()).save(any());
    }

    @Test
    void removerHospede_ExistingId_ShouldDeleteSuccessfully() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);

        ResponseEntity<ResponseVO<HospedeVO>> response = hospedeService.removerHospede(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(repository).deleteById(id);
    }

    @Test
    void removerHospede_NonExistingId_ShouldReturnNotFound() {
        Long id = 999L;

        when(repository.existsById(id)).thenReturn(false);

        ResponseEntity<ResponseVO<HospedeVO>> response = hospedeService.removerHospede(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(repository, never()).deleteById(id);
    }

    @Test
    void getHospede_ExistingId_ShouldReturnHospede() {
        Long id = 1L;
        Hospede hospede = new Hospede();

        when(repository.findById(id)).thenReturn(Optional.of(hospede));

        Hospede result = hospedeService.getHospede(id);

        assertEquals(hospede, result);
    }

    @Test
    void getHospede_NonExistingId_ShouldThrowException() {
        Long id = 999L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        MissingEntityException exception = assertThrows(MissingEntityException.class,
                () -> hospedeService.getHospede(id));

        final String expectedMessage = "Hóspede não encontrado(a) com o id [999]";

        assertEquals(expectedMessage, exception.getMessage());
    }
}
