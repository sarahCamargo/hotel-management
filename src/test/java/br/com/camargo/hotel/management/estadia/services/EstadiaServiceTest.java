package br.com.camargo.hotel.management.estadia.services;

import br.com.camargo.hotel.management.commons.exceptions.BusinessException;
import br.com.camargo.hotel.management.commons.exceptions.MissingEntityException;
import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.estadia.domain.dtos.EstadiaDTO;
import br.com.camargo.hotel.management.estadia.domain.entities.Estadia;
import br.com.camargo.hotel.management.estadia.domain.viewobjects.EstadiaVO;
import br.com.camargo.hotel.management.estadia.factories.EstadiaFactory;
import br.com.camargo.hotel.management.estadia.queries.filters.EstadiaFiltros;
import br.com.camargo.hotel.management.estadia.queries.repositories.IEstadiaQuery;
import br.com.camargo.hotel.management.estadia.repositories.IEstadiaRepository;
import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadiaServiceTest {

    @Mock
    private IEstadiaQuery query;

    @Mock
    private IEstadiaRepository repository;

    @Mock
    private EstadiaFactory factory;

    @Mock
    private CalculadoraEstadiaService calculadoraEstadiaService;

    @InjectMocks
    private EstadiaService estadiaService;

    @Test
    void visualizarEstadias_WithResults_ShouldReturnOk() {
        EstadiaFiltros filtros = new EstadiaFiltros();
        Paginator paginator = Paginator.builder().build();
        Page<EstadiaVO> page = Page.<EstadiaVO>builder()
                .content(Arrays.asList(new EstadiaVO(), new EstadiaVO()))
                .build();

        when(query.findAll(filtros, paginator)).thenReturn(page);

        ResponseEntity<Page<EstadiaVO>> response = estadiaService.visualizarEstadias(filtros, paginator);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).getContent().size());
    }

    @Test
    void visualizarEstadias_NoContent_ShouldReturnNoContent() {
        EstadiaFiltros filtros = new EstadiaFiltros();
        Paginator paginator = Paginator.builder().build();
        Page<EstadiaVO> page = Page.<EstadiaVO>builder()
                .content(Collections.emptyList())
                .build();

        when(query.findAll(filtros, paginator)).thenReturn(page);

        ResponseEntity<Page<EstadiaVO>> response = estadiaService.visualizarEstadias(filtros, paginator);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void buscarEstadia_ExistingId_ShouldReturnEstadia() {
        Long id = 1L;
        Estadia estadia = new Estadia();
        EstadiaVO estadiaVO = new EstadiaVO();

        when(repository.findById(id)).thenReturn(Optional.of(estadia));
        when(factory.toVO(estadia)).thenReturn(estadiaVO);

        ResponseEntity<EstadiaVO> response = estadiaService.buscarEstadia(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(estadiaVO, response.getBody());
    }

    @Test
    void buscarEstadia_NonExistingId_ShouldThrowException() {
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MissingEntityException.class, () -> estadiaService.buscarEstadia(id));
    }

    @Test
    void realizarCheckOut_WithReservaId_ShouldCalculateAndSave() {
        EstadiaDTO dto = EstadiaDTO.builder().reservaId(1L).build();
        Estadia estadia = new Estadia();
        Reserva reserva = new Reserva();
        reserva.setValorTotalPrevisto(new BigDecimal("500.00"));
        estadia.setReserva(reserva);
        Estadia savedEstadia = new Estadia();

        when(repository.findByReserva_IdAndDataHoraSaidaIsNull(1L))
                .thenReturn(Optional.of(estadia));
        when(calculadoraEstadiaService.calcularValorEstadia(any(), any()))
                .thenReturn(new BigDecimal("620.00"));
        when(repository.save(estadia)).thenReturn(savedEstadia);

        ResponseEntity<?> response = estadiaService.realizarCheckOut(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(estadia.getDataHoraSaida());
        verify(calculadoraEstadiaService).calcularValorEstadia(any(), any());
    }

    @Test
    void realizarCheckOut_WithoutRequiredData_ShouldThrowException() {
        EstadiaDTO dto = new EstadiaDTO(); // Sem dados

        BusinessException exception = assertThrows(BusinessException.class,
                () -> estadiaService.realizarCheckOut(dto));

        assertEquals("Informe o id da estadia, id da reserva ou dados do hóspede para check-out.",
                exception.getMessage());
    }
}
