package br.com.camargo.hotel.management.reserva.services;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.commons.exceptions.MissingEntityException;
import br.com.camargo.hotel.management.reserva.factories.ReservaFactory;
import br.com.camargo.hotel.management.reserva.queries.IReservaQuery;
import br.com.camargo.hotel.management.reserva.repositories.IReservaRepository;
import br.com.camargo.hotel.management.reserva.domain.dtos.ReservaDTO;
import br.com.camargo.hotel.management.reserva.domain.enums.StatusReserva;
import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import br.com.camargo.hotel.management.reserva.domain.viewobjects.ReservaVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final IReservaQuery query;
    private final IReservaRepository repository;
    private final ReservaFactory factory;
    private final CalculadoraReservaService calculadoraReservaService;

    public ResponseEntity<Page<ReservaVO>> visualizarReservas(Paginator paginator) {
        final Page<ReservaVO> reservas = query.findAll(paginator);

        if (reservas == null || reservas.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }

    public ResponseEntity<ReservaVO> buscarReserva(Long id) {
        final Reserva reserva = getReserva(id);

        return ResponseEntity.ok(factory.toVO(reserva));
    }

    @Transactional
    public ResponseEntity<ResponseVO<ReservaVO>> reservar(ReservaDTO reservaDTO) {
        if (reservaDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        final BigDecimal valorTotalPrevisto = calculadoraReservaService.calcularValorReserva(reservaDTO);
        final Reserva saved = repository.save(factory.toEntity(reservaDTO, valorTotalPrevisto));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseVO.<ReservaVO>builder()
                        .message("Reserva criada com sucesso.")
                        .data(factory.toVO(saved))
                        .build()
                );
    }

    @Transactional
    public ResponseEntity<ResponseVO<ReservaVO>> alterarReserva(Long id, ReservaDTO reservaDTO) {
        final boolean exists = repository.existsById(id);

        if (!exists) {
            return ResponseEntity.notFound().build();
        }

        final BigDecimal valorTotalPrevisto = calculadoraReservaService.calcularValorReserva(reservaDTO);
        final Reserva saved = repository.save(factory.toEntity(id, valorTotalPrevisto, reservaDTO));

        return ResponseEntity.ok(
                ResponseVO.<ReservaVO>builder()
                        .message("Reserva alterada com sucesso.")
                        .data(factory.toVO(saved))
                        .build());
    }

    @Transactional
    public ResponseEntity<ResponseVO<ReservaVO>> cancelarReserva(Long id) {
        final Reserva reserva = getReserva(id);

        reserva.setStatus(StatusReserva.CANCELADA);

        final Reserva saved = repository.save(reserva);

        return ResponseEntity.ok(
                ResponseVO.<ReservaVO>builder()
                        .message("Reserva cancelada com sucesso.")
                        .data(factory.toVO(saved))
                        .build());

    }

    @Transactional
    public ResponseEntity<ResponseVO<ReservaVO>> deletarReserva(Long id) {
        final boolean exists = repository.existsById(id);

        if (!exists) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.ok(
                ResponseVO.<ReservaVO>builder()
                        .message("Reserva [" + id + "] excluída com sucesso.")
                        .build());
    }

    private Reserva getReserva(Long id) {
        return repository.findById(id).orElseThrow(() -> new MissingEntityException("Reserva", id));
    }
}
