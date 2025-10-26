package br.com.camargo.hotel.management.reserva.services;

import br.com.camargo.hotel.management.commons.exceptions.BusinessException;
import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.commons.exceptions.MissingEntityException;
import br.com.camargo.hotel.management.hospede.domain.entities.Hospede;
import br.com.camargo.hotel.management.hospede.services.HospedeService;
import br.com.camargo.hotel.management.reserva.factories.ReservaFactory;
import br.com.camargo.hotel.management.reserva.queries.IReservaQuery;
import br.com.camargo.hotel.management.reserva.repositories.IReservaRepository;
import br.com.camargo.hotel.management.reserva.domain.dtos.ReservaDTO;
import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import br.com.camargo.hotel.management.reserva.domain.viewobjects.ReservaVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final IReservaQuery query;
    private final IReservaRepository repository;
    private final ReservaFactory factory;
    private final HospedeService hospedeService;
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
        validarDatas(reservaDTO);
        validarReservaDuplicada(reservaDTO);

        final Reserva saved = salvarReserva(null, reservaDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(factory.responseMessage(saved, "Reserva criada com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResponseVO<ReservaVO>> alterarReserva(Long id, ReservaDTO reservaDTO) {
        final boolean exists = repository.existsById(id);

        if (!exists) {
            return ResponseEntity.notFound().build();
        }

        validarDatas(reservaDTO);
        validarReservaDuplicada(reservaDTO);

        final Reserva saved = salvarReserva(id, reservaDTO);

        return ResponseEntity.ok(factory.responseMessage(saved, "Reserva alterada com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResponseVO<ReservaVO>> cancelarReserva(Long id) {
        final Reserva reserva = getReserva(id);

        reserva.cancelar();

        final Reserva saved = repository.save(reserva);

        return ResponseEntity.ok(factory.responseMessage(saved, "Reserva cancelada com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResponseVO<ReservaVO>> deletarReserva(Long id) {
        final boolean exists = repository.existsById(id);

        if (!exists) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    public Reserva getReserva(Long id) {
        return repository.findById(id).orElseThrow(() -> new MissingEntityException("Reserva", id));
    }

    @Transactional
    public Reserva salvarReserva(Long id, ReservaDTO reservaDTO) {
        final Hospede hospede = hospedeService.getHospede(reservaDTO.getHospedeId());

        final BigDecimal valorTotalPrevisto = calculadoraReservaService.calcularValorReserva(
                reservaDTO.getDataEntradaPrevista(),
                reservaDTO.getDataSaidaPrevista(),
                reservaDTO.getAdicionalGaragem()
        );

        return repository.save(factory.fromDTO(id, reservaDTO, valorTotalPrevisto, hospede));
    }

    private void validarDatas(ReservaDTO reservaDTO) {
        LocalDate entrada = reservaDTO.getDataEntradaPrevista();
        LocalDate saida = reservaDTO.getDataSaidaPrevista();

        if (entrada == null || saida == null) {
            throw new BusinessException("As datas de entrada e saída são obrigatórias.");
        }

        if (!saida.isAfter(entrada)) {
            throw new BusinessException("A data de saída deve ser posterior à data de entrada.");
        }
    }

    private void validarReservaDuplicada(ReservaDTO reservaDTO) {
        Long hospedeId = reservaDTO.getHospedeId();
        LocalDate entrada = reservaDTO.getDataEntradaPrevista();
        LocalDate saida = reservaDTO.getDataSaidaPrevista();

        Optional<Reserva> existente = repository.findByHospedeIdAndPeriodo(hospedeId, entrada, saida);

        if (existente.isPresent()) {
            throw new BusinessException("Já existe uma reserva para este hóspede nesse período.");
        }
    }

    public Reserva getReservaAtivaPorHospede(Long hospedeId) {
        return repository.findReservaAtivaPorHospede(hospedeId)
                .orElseThrow(() -> new BusinessException("Nenhuma reserva ativa encontrada para este hóspede."));
    }
}
