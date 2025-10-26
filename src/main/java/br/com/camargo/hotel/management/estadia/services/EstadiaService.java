package br.com.camargo.hotel.management.estadia.services;

import br.com.camargo.hotel.management.commons.exceptions.BusinessException;
import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.commons.exceptions.MissingEntityException;
import br.com.camargo.hotel.management.estadia.factories.EstadiaFactory;
import br.com.camargo.hotel.management.estadia.queries.filters.EstadiaFiltros;
import br.com.camargo.hotel.management.estadia.queries.repositories.IEstadiaQuery;
import br.com.camargo.hotel.management.estadia.repositories.IEstadiaRepository;
import br.com.camargo.hotel.management.estadia.domain.dtos.EstadiaDTO;
import br.com.camargo.hotel.management.estadia.domain.entities.Estadia;
import br.com.camargo.hotel.management.estadia.domain.viewobjects.EstadiaVO;
import br.com.camargo.hotel.management.hospede.domain.entities.Hospede;
import br.com.camargo.hotel.management.hospede.services.HospedeService;
import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import br.com.camargo.hotel.management.reserva.services.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadiaService {

    private final IEstadiaQuery query;
    private final IEstadiaRepository repository;
    private final EstadiaFactory factory;
    private final CalculadoraEstadiaService calculadoraEstadiaService;
    private final HospedeService hospedeService;
    private final ReservaService reservaService;

    public ResponseEntity<Page<EstadiaVO>> visualizarEstadias(EstadiaFiltros filtros, Paginator paginator) {
        final Page<EstadiaVO> estadias = query.findAll(filtros, paginator);

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
        Reserva reserva;

        if (estadiaDTO.getReservaId() != null) {
            reserva = reservaService.getReserva(estadiaDTO.getReservaId());
        } else {
            List<Hospede> hospedes = hospedeService.buscarHospede(
                    estadiaDTO.getNome(),
                    estadiaDTO.getCpf(),
                    estadiaDTO.getTelefone()
            );

            if (hospedes.isEmpty()) {
                throw new BusinessException("Hóspede não encontrado para check-in.");
            }

            reserva = reservaService.getReservaAtivaPorHospede(hospedes.getFirst().getId());
        }

        if (repository.existsByReserva_IdAndDataHoraSaidaIsNull(reserva.getId())) {
            throw new BusinessException("Check-in já realizado para esta reserva.");
        }

        final Estadia saved = repository.save(factory.fromDTO(reserva));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(factory.responseMessage(saved, "Estadia criada com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResponseVO<EstadiaVO>> realizarCheckOut(EstadiaDTO estadiaDTO) {
        Estadia estadia = getEstadiaAtiva(estadiaDTO);
        estadia.checkout();

        final BigDecimal valorFinal = calculadoraEstadiaService.calcularValorEstadia(
                estadia.getReserva().getValorTotalPrevisto(),
                estadia.getDataHoraSaida()
        );

        estadia.setValorTotalFinal(valorFinal);

        final Estadia saved = repository.save(estadia);

        return ResponseEntity.ok(
                ResponseVO.<EstadiaVO>builder()
                        .message("Estadia finalizada com sucesso.")
                        .data(factory.toVO(saved))
                        .build());
    }


    private Estadia getEstadia(Long id) {
        return repository.findById(id).orElseThrow(() -> new MissingEntityException("Estadia", id));
    }

    private Estadia getEstadiaAtiva(EstadiaDTO estadiaDTO) {
        if (estadiaDTO.getReservaId() != null) {
            return repository.findByReserva_IdAndDataHoraSaidaIsNull(estadiaDTO.getReservaId())
                    .orElseThrow(() -> new MissingEntityException("Estadia não encontrada para a reserva informada."));

        }
        if (estadiaDTO.getNome() != null || estadiaDTO.getCpf() != null || estadiaDTO.getTelefone() != null) {
            List<Estadia> estadias = repository.findEstadiaAtivaPorHospede(
                    estadiaDTO.getNome(), estadiaDTO.getCpf(), estadiaDTO.getTelefone());

            if (estadias.isEmpty()) {
                throw new MissingEntityException("Estadia não encontrada para o hóspede informado.");
            }

            return estadias.getFirst();
        }

        throw new BusinessException("Informe o id da estadia, id da reserva ou dados do hóspede para check-out.");
    }
}
