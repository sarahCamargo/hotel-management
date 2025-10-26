package br.com.camargo.hotel.management.reserva.services;

import br.com.camargo.hotel.management.commons.exceptions.BusinessException;
import br.com.camargo.hotel.management.commons.exceptions.MissingEntityException;
import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.hospede.services.HospedeService;
import br.com.camargo.hotel.management.reserva.domain.dtos.ReservaDTO;
import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import br.com.camargo.hotel.management.reserva.domain.viewobjects.ReservaVO;
import br.com.camargo.hotel.management.reserva.factories.ReservaFactory;
import br.com.camargo.hotel.management.reserva.queries.filter.ReservaFiltros;
import br.com.camargo.hotel.management.reserva.queries.repositories.IReservaQuery;
import br.com.camargo.hotel.management.reserva.repositories.IReservaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private IReservaQuery query;

    @Mock
    private IReservaRepository repository;

    @Mock
    private ReservaFactory factory;

    @Mock
    private HospedeService hospedeService;

    @Mock
    private CalculadoraReservaService calculadoraReservaService;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void visualizarReservas_WithResults_ShouldReturnOk() {
        ReservaFiltros filtros = new ReservaFiltros();
        Paginator paginator = Paginator.builder().build();
        Page<ReservaVO> page = Page.<ReservaVO>builder()
                .content(Arrays.asList(new ReservaVO(), new ReservaVO()))
                .build();

        when(query.findAll(filtros, paginator)).thenReturn(page);

        ResponseEntity<Page<ReservaVO>> response = reservaService.visualizarReservas(filtros, paginator);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).getContent().size());
    }

    @Test
    void visualizarReservas_NoContent_ShouldReturnNoContent() {
        ReservaFiltros filtros = new ReservaFiltros();
        Paginator paginator = Paginator.builder().build();
        Page<ReservaVO> page = Page.<ReservaVO>builder()
                .content(Collections.emptyList())
                .build();

        when(query.findAll(filtros, paginator)).thenReturn(page);

        ResponseEntity<Page<ReservaVO>> response = reservaService.visualizarReservas(filtros, paginator);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void buscarReserva_ExistingId_ShouldReturnReserva() {
        Long id = 1L;
        Reserva reserva = new Reserva();
        ReservaVO reservaVO = new ReservaVO();

        when(repository.findById(id)).thenReturn(Optional.of(reserva));
        when(factory.toVO(reserva)).thenReturn(reservaVO);

        ResponseEntity<ReservaVO> response = reservaService.buscarReserva(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservaVO, response.getBody());
    }

    @Test
    void buscarReserva_NonExistingId_ShouldThrowException() {
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MissingEntityException.class, () -> reservaService.buscarReserva(id));
    }

    @Test
    void reservar_WithDuplicateReservation_ShouldThrowException() {
        ReservaDTO dto = ReservaDTO.builder()
                .hospedeId(1L)
                .dataEntradaPrevista(LocalDate.now().plusDays(1))
                .dataSaidaPrevista(LocalDate.now().plusDays(3))
                .build();
        Reserva existingReserva = new Reserva();

        when(repository.findByHospedeIdAndPeriodo(anyLong(), any(), any()))
                .thenReturn(Optional.of(existingReserva));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> reservaService.reservar(dto));

        assertEquals("Já existe uma reserva para este hóspede nesse período.", exception.getMessage());
    }

    @Test
    void alterarReserva_NonExistingId_ShouldReturnNotFound() {
        Long id = 999L;
        ReservaDTO dto = new ReservaDTO();

        when(repository.existsById(id)).thenReturn(false);

        ResponseEntity<ResponseVO<ReservaVO>> response = reservaService.alterarReserva(id, dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(repository, never()).save(any());
    }

    @Test
    void deletarReserva_ExistingId_ShouldDeleteSuccessfully() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);

        ResponseEntity<ResponseVO<ReservaVO>> response = reservaService.deletarReserva(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(repository).deleteById(id);
    }

    @Test
    void deletarReserva_NonExistingId_ShouldReturnNotFound() {
        Long id = 999L;

        when(repository.existsById(id)).thenReturn(false);

        ResponseEntity<ResponseVO<ReservaVO>> response = reservaService.deletarReserva(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(repository, never()).deleteById(id);
    }

    @Test
    void getReservaAtivaPorHospede_ExistingReserva_ShouldReturnReserva() {
        Long hospedeId = 1L;
        Reserva reserva = new Reserva();

        when(repository.findReservaAtivaPorHospede(hospedeId)).thenReturn(Optional.of(reserva));

        Reserva result = reservaService.getReservaAtivaPorHospede(hospedeId);

        assertEquals(reserva, result);
    }

    @Test
    void getReservaAtivaPorHospede_NonExistingReserva_ShouldThrowException() {
        Long hospedeId = 999L;

        when(repository.findReservaAtivaPorHospede(hospedeId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> reservaService.getReservaAtivaPorHospede(hospedeId));

        assertEquals("Nenhuma reserva ativa encontrada para este hóspede.", exception.getMessage());
    }
}
