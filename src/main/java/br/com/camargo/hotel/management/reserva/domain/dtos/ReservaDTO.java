package br.com.camargo.hotel.management.reserva.domain.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {

    @Valid
    @NotNull(message = "Hóspede não pode ser nulo")
    private Long hospedeId;

    @Valid
    @NotNull(message = "Data de entrada prevista não pode ser nula")
    private LocalDate dataEntradaPrevista;

    @Valid
    @NotNull(message = "Data de saída prevista não pode ser nula")
    private LocalDate dataSaidaPrevista;

    @Builder.Default
    private Boolean adicionalGaragem = false;
}
