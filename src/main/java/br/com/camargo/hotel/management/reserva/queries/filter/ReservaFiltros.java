package br.com.camargo.hotel.management.reserva.queries.filter;

import br.com.camargo.hotel.management.reserva.domain.enums.StatusReserva;
import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaFiltros {

    private Long id;
    private Long hospedeId;
    private Long estadiaId;
    private LocalDate dataEntradaPrevista;
    private LocalDate dataSaidaPrevista;
    private Boolean adicionalGaragem;
    private StatusReserva status;

    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }

    public Optional<Long> getHospedeId() {
        return Optional.ofNullable(this.hospedeId);
    }

    public Optional<Long> getEstadiaId() {
        return Optional.ofNullable(this.estadiaId);
    }

    public Optional<LocalDate> getDataEntradaPrevista() {
        return Optional.ofNullable(this.dataEntradaPrevista);
    }

    public Optional<LocalDate> getDataSaidaPrevista() {
        return Optional.ofNullable(this.dataSaidaPrevista);
    }

    public Optional<Boolean> getAdicionalGaragem() {
        return Optional.ofNullable(this.adicionalGaragem);
    }

    public Optional<StatusReserva> getStatus() {
        return Optional.ofNullable(this.status);
    }
}
