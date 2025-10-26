package br.com.camargo.hotel.management.estadia.services;

import br.com.camargo.hotel.management.commons.exceptions.BusinessException;
import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.commons.exceptions.MissingEntityException;
import br.com.camargo.hotel.management.estadia.factories.EstadiaFactory;
import br.com.camargo.hotel.management.estadia.queries.IEstadiaQuery;
import br.com.camargo.hotel.management.estadia.repositories.IEstadiaRepository;
import br.com.camargo.hotel.management.estadia.domain.dtos.EstadiaDTO;
import br.com.camargo.hotel.management.estadia.domain.entities.Estadia;
import br.com.camargo.hotel.management.estadia.domain.viewobjects.EstadiaVO;
import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import br.com.camargo.hotel.management.reserva.domain.enums.StatusReserva;
import br.com.camargo.hotel.management.reserva.services.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EstadiaService {

    private final IEstadiaQuery query;
    private final IEstadiaRepository repository;
    private final ReservaService reservaService;
    private final EstadiaFactory factory;
    private final CalculadoraEstadiaService calculadoraEstadiaService;

    public ResponseEntity<Page<EstadiaVO>> visualizarEstadias(Paginator paginator) {
        final Page<EstadiaVO> estadias = query.findAll(paginator);

        if (estadias == null || estadias.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(estadias);
    }

    public ResponseEntity<EstadiaVO> buscarEstadia(Long id) {
        final Estadia estadia = getEstadia(id);

        return ResponseEntity.ok(factory.toVO(estadia));
    }

    @Transactional
    public ResponseEntity<ResponseVO<EstadiaVO>> realizarCheckIn(EstadiaDTO estadiaDTO) {
        final Reserva reserva = reservaService.getReserva(estadiaDTO.getReservaId());

        if (repository.existsByReserva_IdAndDataHoraSaidaIsNull(reserva.getId())) {
            throw new BusinessException("Check-in já realizado para esta reserva.");
        }

        final Estadia saved = repository.save(factory.fromDTO(reserva));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(factory.responseMessage(saved, "Estadia criada com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResponseVO<EstadiaVO>> realizarCheckOut(Long id) {
        final Estadia estadia = getEstadia(id);

        if (estadia.getDataHoraSaida() != null) {
            throw new BusinessException("Check-out já realizado.");
        }

        estadia.setDataHoraSaida(LocalDateTime.now());

        final BigDecimal valorFinal = calculadoraEstadiaService.calcularValorEstadia(
                estadia.getReserva().getValorTotalPrevisto(),
                estadia.getDataHoraSaida()
        );

        estadia.setValorTotalFinal(valorFinal);
        estadia.getReserva().setStatus(StatusReserva.FINALIZADA);

        final Estadia saved = repository.save(estadia);

        return ResponseEntity.ok(
                ResponseVO.<EstadiaVO>builder()
                        .message("Estadia alterada com sucesso.")
                        .data(factory.toVO(saved))
                        .build());
    }

    private Estadia getEstadia(Long id) {
        return repository.findById(id).orElseThrow(() -> new MissingEntityException("Estadia", id));
    }
}
