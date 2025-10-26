package br.com.camargo.hotel.management.estadia.queries.filters;

import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EstadiaFiltros {

    private Long id;
    private Long reservaId;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<Long> getReservaId() {
        return Optional.ofNullable(reservaId);
    }

    public Optional<LocalDate> getDataEntrada() {
        return Optional.ofNullable(dataEntrada);
    }

    public Optional<LocalDate> getDataSaida() {
        return Optional.ofNullable(dataSaida);
    }
}
